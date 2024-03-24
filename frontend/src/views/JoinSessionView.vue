<template>
  <div class="error" v-if="error">
    <h2>{{ error }}</h2>
    <button class="button" @click="$router.push('/')">
      GO BACK
    </button>
  </div>
  <div v-else class="home">



    <h1>Session ID: {{ sessionID }} </h1>
    <button v-if="isSessionOwner" class="button --danger">
      End Session
       <svg width="46" height="46" fill="none" stroke="currentColor" stroke-linecap="round"
        stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        <path d="M12 2a10 10 0 1 0 0 20 10 10 0 1 0 0-20z"></path>
        <path d="M8 15h8"></path>
        <path d="M9 9h.01"></path>
        <path d="M15 9h.01"></path>
      </svg>
    </button>

    <DisplayOptions v-if="sessionData" :session="sessionData"></DisplayOptions>

  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { getAPIUrl } from '@/utils';
import DisplayOptions from "@/components/DisplayOptions.vue"
import { Session } from '@/types';
export default defineComponent({
  name: 'JoinSessionView',
  components: {
    DisplayOptions
  },
  data() {
    return {
      sessionIDredirect: '',
      error: '',
      isSessionOwner: false,
      sessionData: null as Session | null,

    };
  },
  mounted() {
    this.checkIfSessionExists();

  },
  methods: {
    test() {
      //getSessionInfo
      let url = getAPIUrl() + '/getSessionInfo?session=' + this.sessionID;
      console.log(url);
    },

    getOwnsSession() {
      let sessionID = localStorage.getItem('owns_session');
      //if exists commit to store setOwnsSession
      if (sessionID) {
        this.isSessionOwner = true;
      }
    },

    async checkIfSessionExists() {
      let url = getAPIUrl() + '/getSessionInfo?session=' + this.sessionID;

      let response = await fetch(url);

      let data = await response.json();

      if (data.status == 400) {
        this.error = 'Session not found';
      } else {
        this.sessionData = data;
      }



    },
  },
  computed: {
    sessionID(): string | string[] | undefined {

      let sessionID = this.$route.params.id;
      if (sessionID) {
        this.getOwnsSession();
      }
      return sessionID;
    }

  },
});
</script>


<style lang="scss">
.home {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  height: 100vh;
  gap: 1.5rem;
  padding: 2rem 0;
}

.error {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1rem;
}
</style>
