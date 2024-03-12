<template>
  <div class="error" v-if="error">
    <h2>{{ error }}</h2>
    <button class="button" @click="$router.push('/')">
      GO BACK
    </button>
  </div>
  <div v-else class="home">



    <h1>Session ID: {{ sessionID }}</h1>
    <button class="button" @click="test">test</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { getAPIUrl } from '@/utils';
export default defineComponent({
  name: 'SessionView',
  components: {
  },
  data() {
    return {
      sessionIDredirect: '',
      error: '',

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

    checkIfSessionExists() {
      let url = getAPIUrl() + '/getSessionInfo?session=' + this.sessionID;

      fetch(url)
        .then((response) => {
          if (!response.ok) {
            this.error = 'Session not found';
            return;
          }
          return response.json();
        })
        .then((data) => {
          console.log(data);
        })
        .catch((error) => {
          this.error = error.message;
        });
    },
  },
  computed: {
    sessionID(): string | string[] | undefined {
      return this.$route.params.id;
    },
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
