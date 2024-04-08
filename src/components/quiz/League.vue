<!-- 우선 로그인으로 채워둠 -->

<template>
    <div class="signup-container">
      <header class="header">
        <div class="header-left">
          <div class="logo" @click="goToMain">
            <!-- <img src="@/components/images/newsnippet_logo.png" alt="Newsnippet Logo" /> -->
          </div>
        </div>
        <nav class="header-nav">
          <div class="menu-items">
            <div class="menu-item" @mouseover="showSubmenu('problem')" @mouseout="hideSubmenu('problem')">
              문제
              <div v-show="submenu === 'problem'" class="submenu">
                <div @click="goToProblemPage('오늘의문제')">오늘의 문제</div>
                <div @click="goToProblemPage('전체리그')">전체 리그</div>
                <div @click="goToProblemPage('풀었던문제')">풀었던 문제</div>
              </div>
            </div>
            <div class="menu-item" @mouseover="showSubmenu('league')" @mouseout="hideSubmenu('league')">
              리그
              <div v-show="submenu === 'league'" class="submenu">
                <div @click="goToLeaguePage('오늘의리그')">오늘의 리그</div>
                <div @click="goToLeaguePage('전체리그')">전체 리그</div>
                <div @click="goToLeaguePage('풀었던리그')">풀었던 리그</div>
              </div>
            </div>
          </div>
        </nav>
        <div class="header-right">
          <div class="auth-buttons">
            <button class="btn btn-beige">로그인</button>
          </div>
        </div>
      </header>
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
  
  const router = useRouter();
  const name = ref('');
  const nickname = ref('');
  const email = ref('');
  const password = ref('');
  const submenu = ref(null);
  
  const goToMain = () => {
    router.push('/');
  };
  
  const showSubmenu = (menu) => {
    submenu.value = menu;
  };
  
  const hideSubmenu = (menu) => {
    if (submenu.value === menu) {
      submenu.value = null;
    }
  };
  
  const goToProblemPage = (page) => {
    // 문제 페이지로 이동하는 로직 추가
    console.log(`Moving to problem page: ${page}`);
  };
  
  const goToLeaguePage = (page) => {
    // 리그 페이지로 이동하는 로직 추가
    console.log(`Moving to league page: ${page}`);
  };
  
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
  
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin-bottom: 20px;
    background-color: #333;
    color: #fff;
    padding: 10px 20px;
  }
  
  .header-left,
  .header-right {
    display: flex;
    align-items: center;
  }
  
  .logo {
    font-size: 24px;
    font-weight: bold;
    cursor: pointer;
  }
  
  .header-nav {
    display: flex;
    align-items: center;
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