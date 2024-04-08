<!-- Header.vue -->
<template>
    <header class="header">
      <div class="header-left">
        <div class="logo">
          <!-- <img src="@/components/images/newsnippet_logo.png" alt="Newsnippet Logo" /> -->
        </div>
      </div>
      <nav class="header-nav">
        <div class="menu-items">
          <div class="menu-item" @mouseover="showSubmenu('problem')" @mouseout="hideSubmenu('problem')">
            문제
            <div v-show="submenu === 'problem'" class="submenu">
              <div @click="goToTodayQuiz">오늘의 문제</div>
              <div @click="goToSolvedQuiz">풀었던 문제 확인</div>
            </div>
          </div>
          <div class="menu-item" @mouseover="showSubmenu('league')" @mouseout="hideSubmenu('league')">
            리그
            <div v-show="submenu === 'league'" class="submenu">
              <div @click="goToLeague">전체 리그</div>
            </div>
          </div>
        </div>
      </nav>
      <div class="header-right">
        <div class="auth-buttons">
          <button v-if="showSignUpButton" @click="goToSignUp" class="btn btn-beige">회원가입</button>
          <button v-if="showLoginButton" @click="goToLogin" class="btn btn-beige">로그인</button>
          <button v-if="showLogoutButton" @click="logout" class="btn btn-beige">로그아웃</button>
          <button v-if="showMyPageButton" @click="goToMyPage" class="btn btn-beige">마이페이지</button>
        </div>
      </div>
    </header>
  </template>
  
  <script>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  
  export default {
    props: {
      showSignUpButton: {
        type: Boolean,
        default: false
      },
      showLoginButton: {
        type: Boolean,
        default: false
      },
      showLogoutButton: {
        type: Boolean,
        default: false
      },
      showMyPageButton: {
        type: Boolean,
        default: false
      }
    },
    setup(props) {
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
  
      const goToSignUp = () => {
        router.push('/signup');
      };
  
      const goToLogin = () => {
        router.push('/login');
      };
  
      const logout = () => {
        // 로그아웃 로직 처리
        router.push('/');
      };
  
      const goToMyPage = () => {
        router.push('/my-page');
      };
  
      const goToTodayQuiz = () => {
        router.push('/today-quiz');
      };
  
      const goToSolvedQuiz = () => {
        router.push('/solved-quiz');
      };
  
      const goToLeague = () => {
        router.push('/league');
      };
  
      return {
        submenu,
        showSubmenu,
        hideSubmenu,
        goToSignUp,
        goToLogin,
        logout,
        goToMyPage,
        goToTodayQuiz,
        goToSolvedQuiz,
        goToLeague
      }
    }
  }
  </script>
  
  <style scoped>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    background-color: #ebe4b6;
    color: #000;
    width: 100%;
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
  
  .header-nav {
    display: flex;
    align-items: center;
  }
  
  .menu-items {
    display: flex;
    align-items: center;
    margin-left: 20px; /* 메뉴 아이템을 왼쪽으로 이동 */
  }
  
  .menu-item {
    margin-right: 20px;
    position: relative;
    cursor: pointer;
    color: #000000;
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
  
  body, html {
    margin: 0;
    padding: 0;
  }
  </style>