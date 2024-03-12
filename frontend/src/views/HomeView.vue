<template>
  <div class="home">
   <img class="logo" src="../assets/logo.svg" alt="pikflick logo">
    <h1 class="header-text">pikflik</h1>

    <div class="section" v-if="!enterSessionID">
      <p>Decisions Made Social - Swipe Your Way to the Perfect Pick.</p>
      <div class="contrls">
        <button class="button">CREATE SESSION</button>
        <button class="button --secondary" @click="enterSessionID = !enterSessionID">JOIN SESSION</button>
      </div>
    </div>
    <div class="section" v-if="enterSessionID">
      <h2>Enter Session Code</h2>
      <p>Enter the session code that the session master gave you</p>
      <input maxlength='7' v-model="sessionIDredirect" />

      <div class="contrls">
        <button class="button" @click="$router.push(`/join/${sessionIDredirect}`)">JOIN SESSION</button>
        <button class="button --secondary" @click="enterSessionID = !enterSessionID">BACK</button>
      </div>


    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "HomeView",
  components: {},
  data() {
    return {
      sessionIDredirect: "",
      enterSessionID: false,
    };
  },
});
</script>

<style lang="scss">
.logo {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
}
.home {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  height: 100vh;
  gap: 1.5rem;
  padding: 2rem 0;
}

.section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
}

.contrls {
  display: flex;
  gap: 1rem;
  flex-direction: column;
}
</style>
