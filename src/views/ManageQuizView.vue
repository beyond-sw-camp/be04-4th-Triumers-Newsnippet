<template>
    <header id="manageQuizMenu">
        <RouterLink id="selectQuiz" to="/selectQuiz" active-class="active" replace active>문제 출제</RouterLink>
        <RouterLink id="selectedQuiz" to="/selectedQuiz" active-class="active" replace>문제 확인</RouterLink>
    </header>
    <main>
        <RouterView/>
    </main>
</template>

<script setup>
import { RouterView } from "vue-router";


</script>

<style scoped>

#manageQuizMenu{
    margin:  10px 10px 20px 85px;
}

#selectQuiz, #selectedQuiz{
    margin: 10px;
    padding: 5px 10px;
    background-color: #D5C0AB;
    color: black;
    text-decoration: none;
    border-radius: 30px;
}

.active{
    transition: all 0.3s;
    box-shadow: 3px 3px lightgray;
    border: 1px solid black;
}


</style>