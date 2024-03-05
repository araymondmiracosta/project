<template>
  <div class="home">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="89"
      height="75"
      viewBox="0 0 89 75"
      fill="none"
    >
      <path
        d="M78.7288 44.2753C82.4513 46.2532 82.206 51.6669 78.32 53.3001L33.8366 71.9962C31.2546 73.0814 28.2849 71.8343 27.2518 69.2309L5.96614 15.5883C4.25037 11.2643 8.85174 7.14591 12.9598 9.32871L78.7288 44.2753Z"
        fill="#EA4080"
      />
    </svg>
    <h1 class="header-text">pikflik</h1>
    <p>Decisions Made Social - Swipe Your Way to the Perfect Pick.</p>
    <div class="contrls">
      <button class="button">CREATE SESSION</button>
      <button class="button --secondary">JOIN SESSION</button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "HomeView",
  components: {},
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

.header-text {
  margin-top: 4rem;
}

.contrls {
  display: flex;
  gap: 1rem;
  flex-direction: column;
}
</style>
