<template>
  <div id="app" :class="{ 'dark-mode': isDarkMode }">
    <div class="toggle-button-container">
      <button @click="toggleDarkMode" class="dark-mode-toggle">
        {{ isDarkMode ? 'Light Mode' : 'Dark Mode' }}
      </button>
    </div>
    <ElementLoading :active="isLoading" spinner="spinner" color="#007bff" is-full-screen />
    <RouterView />
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router';
import { computed, ref, onMounted } from 'vue';
import { useStore } from 'vuex';

const store = useStore();
const isDarkMode = computed(() => store.state.isDarkMode);
const isLoading = ref(false);


function toggleDarkMode() {
  store.commit('toggleDarkMode');
}

onMounted(() => {
  isLoading.value = true; // 앱 마운트 시점에서 로딩 상태 시작
  setTimeout(() => {
    isLoading.value = false; // 3초 후에 로딩 상태 종료
  }, 3000);
});

</script>

<style scoped>
@font-face {
  font-family: 'ONE-Mobile-Title';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/ONE-Mobile-Title.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  font-family: 'ONE-Mobile-Title', 'Noto Sans KR';
}

.toggle-button-container {
  position: fixed;
  top: 120px;
  left: 20px;
  z-index: 1000;
}

.dark-mode-toggle {
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>