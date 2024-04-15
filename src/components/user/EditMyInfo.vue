<template>
  <div>
    <Header :isLoggedIn="true"></Header>
    <div class="edit-my-info">
      <div class="form-group">
        <label for="name">이름</label>
        <div class="input-wrapper">
          <input type="text" id="name" v-model="name"/>
        </div>
      </div>
      <div class="form-group">
        <label for="nickname">닉네임</label>
        <div class="input-wrapper">
          <input type="text" id="nickname" v-model="nickname">
          <span v-if="isNicknameValid" class="valid-icon">✓</span>
          <span v-else class="invalid-icon">X</span>
        </div>
      </div>
      
      <div class="form-group">
        <label for="password">비밀번호</label>
        <div class="input-wrapper">
          <input type="password" id="password" v-model="password" />
          <span v-if="isPasswordValid" class="valid-icon">✓</span>
          <span v-else class="invalid-icon">X</span>
        </div>
      </div>
      <button @click="updateUserInfo" :disabled="!isFormValid">변경</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/views/Header.vue';
import axios from 'axios';

const router = useRouter();
const password = ref('');
const name = ref('');
const nickname = ref('');
const userData = ref({});

// 사용자 정보를 가져오는 함수 (백엔드 API 호출)
async function fetchUserData() {
  try {
    const token = localStorage.getItem('token');
    if (token) {
      axios.defaults.headers.common['Authorization'] = token;
      const response = await axios.get('http://localhost:7777/user/my-page');
      userData.value = response.data;
    } else {
      // 토큰이 없을 경우 로그아웃 처리
      handleLogout();
    }
  } catch (error) {
    console.error('Error fetching user data:', error);
    // 에러 발생 시 로그아웃 처리
    handleLogout();
  }
}

const handleLogout = () => {
  localStorage.removeItem('token');
  router.push('/login');
};

const isNicknameValid = computed(() => {
  return nickname.value !== userData.value.nickname && checkNicknameValidity(nickname.value);
});

const isPasswordValid = computed(() => {
  return checkPasswordValidity(password.value);
});

const isFormValid = computed(() => {
  return isNicknameValid.value && isPasswordValid.value;
});

async function updateUserInfo() {
  if (isFormValid.value) {
    try {
      const requestData = {
        name: name.value === '' ? null : name.value,
        nickname: nickname.value === '' ? null : nickname.value
      };
      
      const response = await fetch('http://localhost:7777/auth/modify/info', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('token')
        },
        body: JSON.stringify(requestData)
      });
      // 회원 정보 업데이트 성공
      const responseData = await response.json();
      alert(responseData.message);
      router.push('/my-page');
      
    } catch (error) {
      console.error('Error updating user information:', error);
      alert(error.message);
    }
  } else {
    alert('입력한 정보를 다시 확인해 주세요.');
  }
}

// 닉네임 유효성 검사 함수
function checkNicknameValidity(nickname) {
  const pattern = /^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]{2,10}$/;
  
  return pattern.test(nickname) || nickname === '';
}

// 비밀번호 유효성 검사 함수
function checkPasswordValidity(password) {
  // 비밀번호 유효성 검사 로직 구현
  // 예시: 비밀번호 조건을 만족하는 경우 true 반환
  return true;
}

onMounted(() => {
  fetchUserData();
});
</script>

<style scoped>
.edit-my-info {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
}

.input-wrapper {
  display: flex;
  align-items: center;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.valid-icon,
.invalid-icon {
  margin-left: 5px;
}

.valid-icon {
  color: green;
}

.invalid-icon {
  color: red;
}

button {
  padding: 10px 20px;
  background-color: #f5f5dc;
  color: #333;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #e9e9c9;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>