<template>
    <header>
        <RouterLink to="/selectQuiz" active-class="active" replace active>문제 출제</RouterLink>
        <RouterLink to="/selectedQuiz" active-class="active" replace>문제 확인</RouterLink>
    </header>
    <main>
        <RouterView/>
    </main>
</template>

<script setup>
import { RouterView } from "vue-router";


</script>

<style scoped></style>