<!-- src/components/DarkModeToggle.vue -->
<template>
    <button @click="toggleDarkMode">
      {{ isDarkMode ? 'Light Mode' : 'Dark Mode' }}
    </button>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { useStore } from 'vuex';
  
  const store = useStore();
  const isDarkMode = computed(() => store.state.isDarkMode);
  
  function toggleDarkMode() {
    store.commit('toggleDarkMode');
  }
  </script>