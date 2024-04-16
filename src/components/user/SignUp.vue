<template>
  <Header :isLoggedIn="false"></Header>
  <div class="signup-container">
    <div class="signup-form">
      <h2 id="signUp-title">Sign Up</h2>
      <div class="form-group">
        <input v-model="user.name" placeholder="이름" />
      </div>
      <div class="form-group">
        <input v-model="user.nickname" placeholder="닉네임" style="display: inline-block; width: 81%;"/>&nbsp
        <button v-if="statusNickname === 200" @click="checkNickname" class="btn beige-v-symbol" style="display: inline-block; width: 15%;">✓</button>
        <button v-else @click="checkNickname" class="btn beige-x-symbol" style="display: inline-block; width: 15%;">X</button>
        <div class="description">
          2-10자, 알파벳, 숫자, 한글(특수문자 불가)
        </div>
      </div>
      <div class="form-group">
        <input v-model="user.email" placeholder="이메일" style="display: inline-block; width: 81%;"/>&nbsp
        <button v-if="statusEmail === 200" @click="checkEmail" class="btn beige-v-symbol" style="display: inline-block; width: 15%;">✓</button>
        <button v-else @click="checkEmail" class="btn beige-x-symbol" style="display: inline-block; width: 15%;">X</button>
      </div>
      <div class="form-group">
        <input v-model="user.password" type="password" placeholder="비밀번호" class="form-password"/>
        <div class="description">
          8-12자, 숫자, 대문자, 소문자 각각 1개 이상 포함(이외 문자 불가)
        </div>
      </div>
      <div class="form-group">
        <input v-model="checkPassword" type="password" placeholder="비밀번호 확인" style="display: inline-block; width: 81%;" class="form-password"/>&nbsp
        <span v-if="statusCheckPassword === 200" class="btn beige-v-symbol" style="display: inline-block; width: 15%;">✓</span>
        <span v-else class="btn beige-x-symbol" style="display: inline-block; width: 15%;">X</span>
      </div>
      <button @click="signup" class="signup-btn">회원가입</button>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { useRouter } from 'vue-router';
import Header from '@/views/Header.vue';
import { ref, watch } from 'vue';

const router = useRouter();
const user = {
    name: '',
    nickname: '',
    email: '',
    password: ''
};
const checkPassword = ref('');

const statusNickname = ref(0);
const statusEmail = ref(0);
const statusCheckPassword = ref(0);

function checkNickname() {
  // 닉네임 중복 확인 로직 처리
  return axios.post(`http://localhost:7777/auth/exist/nickname`, user)
  .then(response => {
    statusNickname.value = response.status;
    alert(response.data.message);
  })
  .catch(error => {
    statusNickname.value = error.status;
    alert(error.response.data.message);
  });
}

function checkEmail() {
  // 이메일 중복 확인 로직 처리
  return axios.post(`http://localhost:7777/auth/exist/email`, user)
  .then(response => {
    statusEmail.value = response.status;
    alert(response.data.message);
  })
  .catch(error => {
    statusEmail.value = error.status;
    alert(error.response.data.message);
  });
}

// 비밀번호 확인 로직 처리
watch(checkPassword, async(newVal, oldVal) => {
  if(newVal === user.password) {
    statusCheckPassword.value = 200;
  }
  else {
    statusCheckPassword.value = 400;
  }
});

async function signup() {
  // 회원가입 로직 처리
  return axios.post(`http://localhost:7777/auth/signup`, user)
  .then(response => {
    alert(response.data.message);
    router.push('/');
  })
  .catch(error => {
    alert(error.response.data.message);
  });
};
</script>
  
  <style scoped>
  .signup-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 10px;
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

  .span {
    padding: 8px 16px;
    border-radius: 4px;
    font-size: 14px;
    transition: background-color 0.3s ease;
  }
  
  .btn-beige {
    background-color: #393B63 ;
    color: #FFFFFF;
  }
  
  .btn-beige:hover {
    background-color: #727896;
  }
  
  .signup-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 350px;
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
  
  .signup-btn {
    width: 100%;
    padding: 10px;
    background-color: #393B63;
    color: #ffffff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .signup-btn:hover {
    background-color: #727896;
  }

  .beige-v-symbol {
    background-color: #727896;
    color: #ffffff;
    font-weight: bold;
  }

  .beige-x-symbol {
    background-color: #727896;
    color: #ffffff;
    font-weight: bold;
  }

  .description {
    color: #666;
    font-size: 12px;
    margin-top: 5px;
  }

  #signUp-title{
    margin-bottom: 20px;
  }
  </style>