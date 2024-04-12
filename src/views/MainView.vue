<template>
  <div class="app">
    <Header :isLoggedIn="isLoggedIn" @logout="handleLogout"></Header>
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
const isLoggedIn = ref(false);

const goToLogin = () => {
  router.push('/login');
};

const checkLoginStatus = () => {
  // 로그인 상태 확인 로직 구현
  // 로그인되어 있으면 isLoggedIn.value를 true로 설정
  // 예시: localStorage에서 토큰 확인
  const token = localStorage.getItem('token');
  isLoggedIn.value = !!token;
};

const handleLogout = () => {
  // 로그아웃 로직 구현
  // 예시: localStorage에서 토큰 제거
  localStorage.removeItem('token');
  isLoggedIn.value = false;
};

checkLoginStatus();
</script>

<style scoped>
.app {
  font-family: Arial, sans-serif;
}
.content {
  padding: 20px;
}
</style>