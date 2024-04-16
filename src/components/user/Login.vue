<template>
  <Header :isLoggedIn="false"></Header>
  <div class="login-container">
    <div class="login-form">
      <h2 id="login-title">Login</h2>
      <div class="form-group">
        <input type="email" v-model="user.email" placeholder="이메일" />
      </div>
      <div class="form-group">
        <input type="password" v-model="user.password" placeholder="비밀번호" class="form-password"/>
      </div>
      <button @click="login" class="login-btn">로그인</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import Header from '@/views/Header.vue';
import axios from 'axios';

const router = useRouter();
const user = {
    email: '',
    password: ''
};

async function login() {

  return axios.post(`http://localhost:30001/login`, user)
  .then(response => {
    localStorage.setItem('token', response.headers.get('Authorization'));
    localStorage.setItem('role', response.headers.get('UserRole'));
    router.push('/');

    return response.data;
  })
  .catch(error => {
    const errorMessage = error.message + '\n회원 정보가 일치하지 않습니다.';
    alert(errorMessage);
  });
}

</script>
  
  <style scoped>
  .login-container {
    display: flex;
    flex-direction: column; 
    align-items: center;
    padding: 20px;
    margin: 10px;
  }
  
  .btn {
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
  }
  
  .btn-beige {
    background-color: #393B63;
    color: #ffffff;
  }
  
  .btn-beige:hover {
    background-color: #727896;
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
    background-color: #393B63;
    color: #ffffff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .login-btn:hover {
    background-color: #727896;
  }

  .form-group input::placeholder {
    color: #666; /* placeholder 색상 설정 */
    font-family: 'ONE-Mobile-Title', sans-serif;
    font-weight: normal;
    font-style: normal;
  }

  .form-password input::placeholder {
    color: #666;
    font-family: 'ONE-Mobile-Title', sans-serif;
  }

  .form-password {
    width: 100%;
    margin-bottom: 1px;
    font-family: "Roboto", sans-serif;
    src: url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');
    font-weight: 1000;
    font-style: normal;
  }

  #login-title{
    margin-bottom: 20px;
  }
  </style>