<template>
  <main>
    <RouterView />
  </main>
</template>

<script setup>
import { RouterView } from 'vue-router';

</script>
<style scoped>
@font-face {
    font-family: 'ONE-Mobile-Title';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/ONE-Mobile-Title.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

main {
  margin: 10px;
  font-family:  'ONE-Mobile-Title', 'Noto Sans KR';
  background-size: 100vh;
  background-color: #FFFBF8;
  min-height: 100vh;
  height: auto;
}
</style>