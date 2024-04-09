<template>
  <div>
    <Header :showLogoutButton="true"></Header>
    <div class="my-page">
      <div class="profile-section">
        <div class="profile-picture">
          <img :src="profilePicture" alt="Profile Picture" @click="showModal = true" />
          <div class="user-info">
            <div class="info-item">
              <span class="label">이름</span>
              <div class="value-box">{{ userData.name }}</div>
            </div>
            <div class="info-item">
              <span class="label">이메일</span>
              <div class="value-box">{{ userData.email }}</div>
            </div>
            <div class="info-item">
              <span class="label">닉네임</span>
              <div class="value-box">{{ userData.nickname }}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="stats-section">
        <div class="stats-item">
          <span class="label">푼 문제</span>
          <div class="value-box">{{ userData.solvedCnt }}개</div>
        </div>
        <div class="stats-item">
          <span class="label">맞힌 문제</span>
          <div class="value-box">{{ userData.correctCnt }}개</div>
        </div>
        <div class="stats-item">
          <span class="label">정답률</span>
          <div class="value-box">{{ calculateAccuracy() }}%</div>
        </div>
      </div>
      <div class="button-section">
        <button class="edit-btn" @click="goToEditMyInfo">회원정보 수정</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/views/Header.vue';

const router = useRouter();
const userData = ref({});
const showModal = ref(false);

// 사용자 정보를 가져오는 함수 (백엔드 API 호출)
async function fetchUserData() {
  try {
    const response = await fetch('/api/user');
    userData.value = await response.json();
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
}

// 정답률 계산 함수
function calculateAccuracy() {
  if (userData.value.solvedCnt === 0) {
    return 0;
  }
  return Math.round((userData.value.correctCnt / userData.value.solvedCnt) * 100);
}

// 회원정보 수정 페이지로 이동
function goToEditMyInfo() {
  router.push('/edit-my-info');
}

onMounted(() => {
  fetchUserData();
});
</script>

<style scoped>
.my-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.profile-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-picture img {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  cursor: pointer;
  margin-right: 20px;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.info-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.label {
  width: 80px;
  font-weight: bold;
}

.value-box {
  border: 1px solid #ccc;
  padding: 5px;
  flex-grow: 1;
}

.stats-section {
  display: flex;
  justify-content: space-around;
  width: 100%;
  margin-bottom: 20px;
}

.stats-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.button-section {
  margin-top: 20px;
}

.edit-btn {
  padding: 8px 16px;
  background-color: #f5f5dc;
  color: #333;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.edit-btn:hover {
  background-color: #e9e9c9;
}
</style>