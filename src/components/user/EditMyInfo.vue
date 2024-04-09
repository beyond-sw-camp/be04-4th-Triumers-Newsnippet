<template>
  <div>
    <Header :showLogoutButton="true" :showMyPageButton="true"></Header>
    <div class="edit-my-info">
      <div class="form-group">
        <label for="name">이름</label>
        <div class="input-wrapper">
          <input type="text" id="name" v-model="name" />
        </div>
      </div>
      <div class="form-group">
        <label for="nickname">닉네임</label>
        <div class="input-wrapper">
          <input type="text" id="nickname" v-model="nickname" />
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
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/views/Header.vue';

const router = useRouter();
const name = ref('');
const nickname = ref('');
const password = ref('');
const userData = ref({
  nickname: '', // 현재 닉네임 값을 할당해야 합니다.
});

const isNicknameValid = computed(() => {
  return nickname.value !== userData.nickname && checkNicknameValidity(nickname.value);
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
      const response = await fetch('/api/user', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: name.value,
          nickname: nickname.value,
          password: password.value,
        }),
      });

      if (response.ok) {
        // 회원 정보 업데이트 성공
        router.push('/my-page');
      } else {
        // 회원 정보 업데이트 실패
        console.error('Failed to update user information');
        alert('회원 정보 업데이트에 실패했습니다. 다시 시도해 주세요.');
      }
    } catch (error) {
      console.error('Error updating user information:', error);
      alert('회원 정보 업데이트 중 오류가 발생했습니다. 다시 시도해 주세요.');
    }
  } else {
    alert('입력한 정보를 다시 확인해 주세요.');
  }
}

// 닉네임 유효성 검사 함수
function checkNicknameValidity(nickname) {
  // 닉네임 유효성 검사 로직 구현
  // 예시: 닉네임 조건을 만족하는 경우 true 반환
  return true;
}

// 비밀번호 유효성 검사 함수
function checkPasswordValidity(password) {
  // 비밀번호 유효성 검사 로직 구현
  // 예시: 비밀번호 조건을 만족하는 경우 true 반환
  return true;
}
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