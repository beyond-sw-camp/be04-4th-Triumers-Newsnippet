<template>
  <div class="login-container">
    <Header :showSignUpButton="true"></Header>
    <div class="login-form">
      <h2>Login</h2>
      <div class="form-group">
        <input v-model="email" placeholder="이메일" />
      </div>
      <div class="form-group">
        <input v-model="password" type="password" placeholder="비밀번호" />
      </div>
      <button @click="login" class="login-btn">로그인</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/views/Header.vue';

const router = useRouter();
const email = ref('');
const password = ref('');
const submenu = ref(null);

const login = () => {
  // 로그인 로직 처리
  // 예시: 로그인 성공 시 localStorage에 토큰 저장
  localStorage.setItem('token', 'your_token_here');
  router.push('/');
};
</script>
  
  <style scoped>
  .login-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
  }
  
  .btn {
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
  }
  
  .btn-beige {
    background-color: #f5f5dc;
    color: #333;
  }
  
  .btn-beige:hover {
    background-color: #e9e9c9;
  }
  
  .login-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 300px;
  }
  
  .form-group {
    width: 100%;
    margin-bottom: 10px;
  }
  
  .form-group input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .login-btn {
    width: 100%;
    padding: 10px;
    background-color: #f5f5dc;
    color: #333;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .login-btn:hover {
    background-color: #e9e9c9;
  }
  </style>