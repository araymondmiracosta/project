<template>
  <div class="header">
    <span>id: {{ sessionID }} </span>
    <div class="btn-cluster">
      <button v-if="isSessionOwner" class="button --danger --small" @click="endSession">
        End Session
        <svg
          fill="none"
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M12 2a10 10 0 1 0 0 20 10 10 0 1 0 0-20z"></path>
          <path d="m4.93 4.93 14.14 14.14"></path>
        </svg>
      </button>
      <button class="button --secondary --small" @click="showQR()">
        Share
        <svg
          fill="none"
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M18 2a3 3 0 1 0 0 6 3 3 0 1 0 0-6z"></path>
          <path d="M6 9a3 3 0 1 0 0 6 3 3 0 1 0 0-6z"></path>
          <path d="M18 16a3 3 0 1 0 0 6 3 3 0 1 0 0-6z"></path>
          <path d="m8.59 13.51 6.83 3.98"></path>
          <path d="m15.41 6.51-6.82 3.98"></path>
        </svg>
      </button>
    </div>
  </div>
  <Dialog
    v-model:visible="displayQRmodal"
    modal
    header="Share Session"
    :style="modal_style"
  >
    <QRCodeVue3
      :value="`http://localhost:8081/join/${sessionID}`"
      :dotsOptions="{
        type: 'dots',
        color: '#ea4080',
        gradient: {
          type: 'linear',
          rotation: 0,
          colorStops: [
            { offset: 0, color: '#ea4080' },
            { offset: 1, color: '#ea4080' },
          ],
        },
      }"
    />
  </Dialog>
  <div class="error" v-if="error">
    <h2>{{ error }}</h2>
    <button class="button" @click="$router.push('/')">GO BACK</button>
  </div>
  <div v-else class="home">
    <DisplayOptions v-if="sessionData" :session="sessionData"></DisplayOptions>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { getAPIUrl } from "@/utils";
import DisplayOptions from "@/components/DisplayOptions.vue";
import QRCodeVue3 from "qrcode-vue3";

import Dialog from "primevue/dialog";
import { Session } from "@/types";
export default defineComponent({
  name: "JoinSessionView",
  components: {
    DisplayOptions,
    Dialog,
    QRCodeVue3,
  },
  data() {
    return {
      sessionIDredirect: "",
      error: "",
      isSessionOwner: false,
      sessionData: null as Session | null,
      displayQRmodal: false,
      modal_style: {
        display: "flex",
        maxHeight: "95%",
        maxWidth: "800px",
        width: "90%",
        padding: "24px",
        flexDirection: "column",
        borderRadius: "26px",
        background: "var(--bg-main, #fff)",
        boxShadow: "0px 3px 5px 0px rgba(93, 103, 128, 0.14)",
        zIndex: "1000",
      },
    };
  },
  mounted() {
    this.checkIfSessionExists();
  },
  methods: {
    async showQR() {
      this.displayQRmodal = true;
      console.log("Show QR");
    },

    async endSession() {
      let url = getAPIUrl() + "/endSession?session=" + this.sessionID;
      await fetch(url);
      this.$router.push("/results/" + this.sessionID);
    },

    getOwnsSession() {
      let sessionID = localStorage.getItem("owns_session");
      //if exists commit to store setOwnsSession
      if (sessionID) {
        this.isSessionOwner = true;
      }
    },

    async checkIfSessionExists() {
      if (!this.sessionID) {
        this.$router.push("/");
        return;
      }
      let url = getAPIUrl() + "/getSessionInfo?session=" + this.sessionID;

      let response = await fetch(url);

      let data = await response.json();

      if (data.status == 400) {
        this.error = "Session not found";
      } else {
        this.sessionData = data;
        console.log("Sesson Data: ", this.sessionData);
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
    },
    sessionLink(): string {
      const baseURL = "http://localhost:8081";
      return `${baseURL}/join/${this.sessionID}`;
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

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;

  span {
    font-size: 1.5rem;
    font-weight: 600;
  }
}

.btn-cluster {
  display: flex;
  gap: 1rem;
}
</style>
