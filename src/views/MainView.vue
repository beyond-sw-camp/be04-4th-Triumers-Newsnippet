<template>
  <div class="app">
    <Header :isLoggedIn="isLoggedIn" @logout="handleLogout"></Header>
    <div class="content">
      <div class="banner">
        <img src="@/assets/images/newsnippetLogo.png" alt="Newsnippet Logo"/>
      </div>

      <div class="illustration">
  <img src="@/assets/images/main.png" alt="Main illustration" class="main-img" />
  <div class="image-group">
    <img src="@/assets/images/check.png" alt="Check" class="first-img" />
    <img src="@/assets/images/questionmark.png" alt="Question mark" class="second-img" />
    <img src="@/assets/images/wrong.png" alt="Wrong" class="third-img" />
  </div>
  <div class="description">
    <p>NewSnippet은 매일 업데이트되는 뉴스를 기반으로 사용자들에게 다양한 주제의 시사상식 퀴즈를 제공합니다. <br><br> 사용자들은 퀴즈를 풀면서 지식을 쌓을 뿐만 아니라, 뉴스를 통해 정보를 습득하고 더 나은 시사상식을 갖출 수 있습니다.</p>
  </div>
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
.content {
  padding: 20px;
}

.banner {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  animation: bounceUpDown 2s infinite alternate;
  background-color: transparent; /* 배경색을 투명으로 설정 */
}

.illustration {
  display: flex;
  flex-wrap: nowrap;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 20px;
  background-color: transparent;
  position: relative; /* 자식 요소 위치 기준 설정 */
}

.illustration .main-img {
  max-width: 60%;
  max-height: 60%;
  object-fit: contain;
  margin-left: 40px;
  margin-right: 20px;
}

.illustration .first-img,
.illustration .second-img,
.illustration .third-img {
  object-fit: contain;
  margin-right: 15px; /* 이미지 간 간격 */
  align-self: flex-start; /* 이미지들을 위로 정렬 */
  position: absolute; /* 절대 위치 지정 */
  left: 670px; /* 왼쪽 여백 조절 */

}

.illustration .first-img {
  max-width: 20%;
  max-height: 20%;
  top: 80px;
}

.illustration .second-img {
  max-width: 25%;
  max-height: 25%;
  left: 900px; /* 두 번째 이미지 왼쪽 여백 조절 */
  top: 200px; /* 아래로 20px 이동 */
}

.illustration .third-img {
  max-width: 18%;
  max-height: 18%;
  left: 1100px; /* 세 번째 이미지 왼쪽 여백 조절 */
  bottom: 850px;
}

@keyframes scaleUpDown {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}

.first-img {
  animation: scaleUpDown 3s infinite;
}

.second-img {
  animation: scaleUpDown 3.5s infinite;
}

.third-img {
  animation: scaleUpDown 4s infinite;
}

@keyframes bounceUpDown {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px); /* 위로 이동할 거리 */
  }
}

.description {
  margin-left: 100px;
  margin-top: 300px;
  max-width: 400px;
  text-align: center;
  align-self: flex-start; /* 텍스트 영역을 위쪽 정렬 */
}

.description p {
  font-size: 30px;
  line-height: 1.5;
  color: #555;
}

</style>