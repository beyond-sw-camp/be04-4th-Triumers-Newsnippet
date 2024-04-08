<template>
    <div class="signup-container">
    <Header :showLoginButton="true"></Header>

      <div class="signup-form">
        <h2>Sign Up</h2>
        <div class="form-group">
          <input v-model="name" placeholder="이름" />
        </div>
        <div class="form-group">
          <input v-model="nickname" placeholder="닉네임" />
        </div>
        <div class="form-group">
          <input v-model="email" placeholder="이메일" />
        </div>
        <div class="form-group">
          <input v-model="password" type="password" placeholder="비밀번호" />
        </div>
        <button @click="signup" class="signup-btn">회원가입</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import Header from '@/views/Header.vue';
  
  const router = useRouter();
  const name = ref('');
  const nickname = ref('');
  const email = ref('');
  const password = ref('');
  const submenu = ref(null);
  
  
  const signup = () => {
    // 회원가입 로직 처리
    router.push('/');
  };
  </script>
  
  <style scoped>
  .signup-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
  }
  
  .menu-items {
    display: flex;
    align-items: center;
  }
  
  .menu-item {
    margin-right: 20px;
    position: relative;
    cursor: pointer;
    color: #fff;
    padding: 10px;
  }
  
  .submenu {
    position: absolute;
    top: 100%;
    left: 0;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    padding: 10px;
    z-index: 1;
    color: #333;
    min-width: 150px;
  }
  
  .submenu div {
    padding: 10px;
    cursor: pointer;
    white-space: nowrap;
  }
  
  .submenu div:hover {
    background-color: #f0f0f0;
  }
  
  .auth-buttons {
    display: flex;
    align-items: center;
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
  
  .signup-form {
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
  
  .signup-btn {
    width: 100%;
    padding: 10px;
    background-color: #f5f5dc;
    color: #333;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .signup-btn:hover {
    background-color: #e9e9c9;
  }
  </style>