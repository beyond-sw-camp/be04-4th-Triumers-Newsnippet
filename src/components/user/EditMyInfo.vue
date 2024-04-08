<template>
    <div>
      <Header :showLogoutButton="true"></Header>/>
      <div class="my-page">
        <div class="profile-picture">
          <img :src="profilePicture" alt="Profile Picture" @click="showModal = true" />
        </div>
        <div class="user-info">
          <div class="info-item">
            <span class="label">이름</span>
            <span class="value">{{ name }}</span>
          </div>
          <div class="info-item">
            <span class="label">닉네임</span>
            <span class="value">{{ nickname }}</span>
          </div>
          <div class="info-item">
            <span class="label">이메일</span>
            <span class="value">{{ email }}</span>
          </div>
          <div class="info-item">
            <span class="label">푼 문제</span>
            <span class="value">{{ solvedQuestions }}</span>
          </div>
          <div class="info-item">
            <span class="label">맞힌 문제</span>
            <span class="value">{{ correctAnswers }}</span>
          </div>
          <div class="info-item">
            <span class="label">정답률</span>
            <span class="value">{{ accuracy }}%</span>
          </div>
          <button class="edit-btn" @click="goToEditMyInfo">회원정보 수정</button>
        </div>
      </div>
      <teleport to="body">
        <div v-if="showModal" class="modal">
          <div class="modal-content">
            <span class="close" @click="showModal = false">&times;</span>
            <img :src="profilePicture" alt="Profile Picture" />
          </div>
        </div>
      </teleport>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import Header from '@/views/Header.vue';
  
  const router = useRouter();
  
  const profilePicture = ref(''); // 프로필 사진 URL
  const name = ref(''); // 이름
  const nickname = ref(''); // 닉네임
  const email = ref(''); // 이메일
  const solvedQuestions = ref(0); // 푼 문제 수
  const correctAnswers = ref(0); // 맞힌 문제 수
  const accuracy = ref(0); // 정답률
  
  const showModal = ref(false); // 모달 팝업 상태
  
  // 사용자 정보를 가져오는 함수 (백엔드 API 호출)
  async function fetchUserData() {
    try {
      const response = await fetch('/api/user');
      const data = await response.json();
  
      profilePicture.value = data.profilePicture;
      name.value = data.name;
      nickname.value = data.nickname;
      email.value = data.email;
      solvedQuestions.value = data.solvedQuestions;
      correctAnswers.value = data.correctAnswers;
      accuracy.value = data.accuracy;
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  }
  
  // 회원정보 수정 페이지로 이동
  function goToEditMyInfo() {
    router.push('/edit-my-info');
  }
  
  fetchUserData(); // 컴포넌트 마운트 시 사용자 정보 가져오기
  </script>
  
  <style scoped>
  .my-page {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
  }
  
  .profile-picture img {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    object-fit: cover;
    cursor: pointer;
  }
  
  .user-info {
    margin-left: 40px;
  }
  
  .info-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }
  
  .label {
    font-weight: bold;
    margin-right: 10px;
  }
  
  .edit-btn {
    margin-top: 20px;
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
  
  .modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
  }
  
  .modal-content {
    position: relative;
    max-width: 80%;
    max-height: 80%;
  }
  
  .close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
  }
  
  .close:hover,
  .close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
  }
  </style>