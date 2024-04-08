<template>
    <div class="app">
        <Header :showLoginButton="true" :showSignUpButton="true"></Header>
      <div class="content">
        <div class="banner">
          <!-- <img src="path/to/banner.png" alt="Banner" /> -->
        </div>
        <div class="illustration">
          <!-- <img src="path/to/illustration.png" alt="Illustration" /> -->
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import Header from '@/views/Header.vue';
  
  const router = useRouter();
  const submenu = ref(null);
  
  
  </script>
  
  <style scoped>
  .app {
    font-family: Arial, sans-serif;
  }
  
  
  
  .content {
    padding: 20px;
  }
  </style>