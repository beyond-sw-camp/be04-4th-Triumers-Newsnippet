<template>
  <div>
    <Header :isLoggedIn="true"></Header>
    <div v-if="$store.state.isLoading" class="loading-spinner">
        <div class="spinner"></div>
    <!-- 로딩 스피너 또는 로딩 표시 -->
    </div>
    <div v-else class="container">
      <h2>문제 상세</h2>
      <hr>
      <div class="quiz-container" v-if="quiz">
        <div class="quiz-content">
          <p class="quiz-question">문제: {{ quiz.content }}</p>
          <ul class="options">
            <li
              :class="{ 'selected-option': quiz.selectedOption === quiz.optionA, 'correct-option': quiz.answer === quiz.optionA }">
              <span class="option-label"
                :class="{ correct: quiz.answer === optionText[0], incorrect: quiz.selectedOption === optionText[0] && quiz.answer !== optionText[0] }">A</span>
              <span class="option-text">{{ quiz.optionA }}</span>
            </li>
            <li
              :class="{ 'selected-option': quiz.selectedOption === quiz.optionB, 'correct-option': quiz.answer === quiz.optionB }">
              <span class="option-label"
                :class="{ 'correct': quiz.answer === optionText[1], 'incorrect': quiz.selectedOption === optionText[1] && quiz.answer !== optionText[1] }">B</span>
              <span class="option-text">{{ quiz.optionB }}</span>
            </li>
            <li
              :class="{ 'selected-option': quiz.selectedOption === quiz.optionC, 'correct-option': quiz.answer === quiz.optionC }">
              <span class="option-label"
                :class="{ 'correct': quiz.answer === optionText[2], 'incorrect': quiz.selectedOption === optionText[2] && quiz.answer !== optionText[2] }">C</span>
              <span class="option-text">{{ quiz.optionC }}</span>
            </li>
            <li
              :class="{ 'selected-option': quiz.selectedOption === quiz.optionD, 'correct-option': quiz.answer === quiz.optionD }">
              <span class="option-label"
                :class="{ 'correct': quiz.answer === optionText[3], 'incorrect': quiz.selectedOption === optionText[3] && quiz.answer !== optionText[3] }">D</span>
              <span class="option-text">{{ quiz.optionD }}</span>
            </li>
          </ul>
        </div>
        <div class="explanation-container">
          <div class="news-link">
            <h4>원본 링크</h4>
            <a :href="quiz.newsLink" target="_blank">{{ quiz.newsLink }}</a>
          </div>
          <div class="explanation">
            <h4>해설</h4>
            <p>{{ quiz.explanation }}</p>
          </div>
        </div>
      </div>
      <button @click="goBack" class="back-button">목록으로</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/views/Header.vue';
import axios from 'axios';
import { useStore } from 'vuex';

const store = useStore();

const route = useRoute();
const router = useRouter();
const quizId = route.params.id;
const quiz = ref(null);

const optionText = ["A", "B", "C", "D"];

onMounted(fetchSolvedQuiz);

async function fetchSolvedQuiz() {

  store.commit('setLoading', true);
  try {
    const token = localStorage.getItem('token');
    if (token) {
      axios.defaults.headers.common['Authorization'] = token;
      const response = await axios.post('http://localhost:7777/solved/find',
        {
          quizId: quizId
        });
        quiz.value = response.data;
    } else {
      alert("잘못된 접근입니다.");
    }
  } catch (error) {
    console.log(error);
  } finally {
    store.commit('setLoading', false);
  }
}

const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
@import url('@/styles/quiz/SolvedQuizDetail.css');
</style>