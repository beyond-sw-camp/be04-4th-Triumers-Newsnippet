<template>
    <Header :isLoggedIn="isLoggedIn" @logout="handleLogout"></Header>
<div id="wrapper">
    <header id="manageQuizMenu">
        <RouterLink id="selectQuiz" to="/manage/selectQuiz" active-class="active" replace active>문제 출제</RouterLink>
        <RouterLink id="selectedQuiz" to="/manage/selectedQuiz" active-class="active" replace>문제 확인</RouterLink>
    </header>
    <main>
        <RouterView/>
    </main>
</div>
</template>

<script setup>
import { RouterView } from "vue-router";
import Header from '@/views/Header.vue';
</script>

<style scoped>
@import url('@/assets/css/manage/ManageQuiz.css');
</style>