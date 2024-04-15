<template>
  <header class="header">
    <div class="header-left">
      <div class="logo">
        <img src="@/assets/images/newsnippetLogo.png" alt="Newsnippet Logo" @click="navigateTo('')" />
      </div>
    </div>
    <nav class="header-nav">
      <div class="menu-items">
        <div class="menu-item" @mouseover="showSubmenu('problem')" @mouseout="hideSubmenu('problem')">
          문제
          <div v-show="submenu === 'problem'" class="submenu">
            <div @click="handleNavigation('today-quiz')">오늘의 문제</div>
            <div @click="handleNavigation('solved-quiz-list')">풀었던 문제 확인</div>
          </div>
        </div>
        <div class="menu-item" @mouseover="showSubmenu('league')" @mouseout="hideSubmenu('league')">
          리그
          <div v-show="submenu === 'league'" class="submenu">
            <div @click="handleNavigation('league')">전체 리그</div>
          </div>
        </div>
      </div>
    </nav>
    <div class="header-right">
      <div class="auth-buttons">
        <button v-if="!isLoggedIn" @click="navigateTo('signup')" class="btn btn-beige">회원가입</button>
        <button v-if="!isLoggedIn" @click="navigateTo('login')" class="btn btn-beige">로그인</button>
        <button v-if="isLoggedIn" @click="logout" class="btn btn-beige">로그아웃</button>
        <button v-if="isLoggedIn" @click="navigateTo('my-page')" class="btn btn-beige">마이페이지</button>
        <button v-if="isLoggedIn" @click="handleNavigation('manage/selectQuiz')" class="btn btn-beige">퀴즈 관리</button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps({
  isLoggedIn: {
    type: Boolean,
    required: true,
  },
});

const router = useRouter();
const submenu = ref(null);

const showSubmenu = (menu) => {
  submenu.value = menu;
};

const hideSubmenu = (menu) => {
  if (submenu.value === menu) {
    submenu.value = null;
  }
};

const navigateTo = (path) => {
  router.push(`/${path}`);
};

const handleNavigation = (path) => {
  if (props.isLoggedIn) {
    router.push(`/${path}`);
  } else {
    alert('로그인 후 사용 가능합니다.');
    router.push('/login');
  }
};

const logout = () => {
  alert('로그아웃 되었습니다.');
  localStorage.removeItem('token');
  emit('logout');
  router.push('/');
};

const emit = defineEmits(['logout']);
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  /* background-color: #F4F3F6; */
  color: #000;
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.header-left,
.header-right {
  display: flex;
  align-items: center;
}

.logo {
  font-size: 24px;
  font-weight: bold;
}

.logo > img {
  width: 200px;
}

.header-nav {
  display: flex;
  align-items: center;
}

.menu-items {
  display: flex;
  align-items: center;
  margin-right: 550px; /* 메뉴 아이템을 왼쪽으로 이동 */
}

.menu-item {
  margin-right: 30px;
  position: relative;
  cursor: pointer;
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
  margin: 5px;
  background-color: #393b63;
  color: #ffffff;
}

.btn-beige:hover {
  background-color: #cac5bf;
}

body,
html {
  margin: 0;
  padding: 0;
}
</style>