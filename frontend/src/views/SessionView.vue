<template>
  <div class="home">
    SessionView {{ sessionID }}
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'SessionView',
  components: {
  },
  computed: {
    sessionID() {
      return this.$route.params.id;
    },
  },
});
</script>
