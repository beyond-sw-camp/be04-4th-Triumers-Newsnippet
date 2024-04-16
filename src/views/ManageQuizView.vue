<template>
    <Header :isLoggedIn="true" :userRole="userRole"></Header>
    <div id="wrapper">
        <h2>퀴즈 관리</h2>
        <hr>
        <header id="manageQuizMenu">
            <RouterLink id="selectQuiz" to="/manage/selectQuiz" active-class="active" replace active>문제 출제</RouterLink>
            <RouterLink id="selectedQuiz" to="/manage/selectedQuiz" active-class="active" replace>문제 확인</RouterLink>
        </header>
        <main>
            <RouterView />
        </main>
    </div>
</template>

<script setup>
import { RouterView } from "vue-router";
import Header from '@/views/Header.vue';

const userRole = localStorage.getItem('userRole');
</script>

<style scoped>
@import url('@/styles/manage/ManageQuiz.css');
</style>