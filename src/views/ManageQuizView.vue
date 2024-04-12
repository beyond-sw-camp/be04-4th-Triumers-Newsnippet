<template>
<div id="wrapper">
    <header id="manageQuizMenu">
        <RouterLink id="selectQuiz" to="/selectQuiz" active-class="active" replace active>문제 출제</RouterLink>
        <RouterLink id="selectedQuiz" to="/selectedQuiz" active-class="active" replace>문제 확인</RouterLink>
    </header>
    <main>
        <RouterView/>
    </main>
</div>
</template>

<script setup>
import { RouterView } from "vue-router";

</script>

<style scoped>
@import url('@/assets/css/manage/ManageQuiz.css');
</style>