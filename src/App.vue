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
  font-family: 'RIDIBatang';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/RIDIBatang.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: 'MICEMyungjo';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEMyungjo.woff2') format('woff2');
  font-weight: normal;
  font-style: normal;
}

main {
  font-family: 'MICEMyungjo', 'RIDIBatang', 'Noto Sans KR';
  background-size: 100vh;
  background-color: #EDE8E3;
  min-height: 100vh;
  height: auto;
}
</style>