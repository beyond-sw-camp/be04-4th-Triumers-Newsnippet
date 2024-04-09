<template>
    <div>
      <Header :isLoggedIn="true"></Header>
      <div class="container">
        <h2>문제 상세</h2>
        <div class="quiz-container">
          <div class="quiz-content">
            <p class="quiz-question">문제: {{ quiz.content }}</p>
            <ul class="options">
              <li :class="{ 'selected-option': quiz.selectedOption === quiz.optionA, 'correct-option': quiz.answer === quiz.optionA }">
                <span class="option-label" :class="{ 'correct': quiz.answer === quiz.optionA, 'incorrect': quiz.selectedOption === quiz.optionA && quiz.answer !== quiz.optionA }">A</span>
                <span class="option-text">{{ quiz.optionA }}</span>
              </li>
              <li :class="{ 'selected-option': quiz.selectedOption === quiz.optionB, 'correct-option': quiz.answer === quiz.optionB }">
                <span class="option-label" :class="{ 'correct': quiz.answer === quiz.optionB, 'incorrect': quiz.selectedOption === quiz.optionB && quiz.answer !== quiz.optionB }">B</span>
                <span class="option-text">{{ quiz.optionB }}</span>
              </li>
              <li :class="{ 'selected-option': quiz.selectedOption === quiz.optionC, 'correct-option': quiz.answer === quiz.optionC }">
                <span class="option-label" :class="{ 'correct': quiz.answer === quiz.optionC, 'incorrect': quiz.selectedOption === quiz.optionC && quiz.answer !== quiz.optionC }">C</span>
                <span class="option-text">{{ quiz.optionC }}</span>
              </li>
              <li :class="{ 'selected-option': quiz.selectedOption === quiz.optionD, 'correct-option': quiz.answer === quiz.optionD }">
                <span class="option-label" :class="{ 'correct': quiz.answer === quiz.optionD, 'incorrect': quiz.selectedOption === quiz.optionD && quiz.answer !== quiz.optionD }">D</span>
                <span class="option-text">{{ quiz.optionD }}</span>
              </li>
            </ul>
          </div>
          <div class="explanation-container">
            <div class="news-link">
              <h3>원본 링크</h3>
              <a :href="quiz.newsLink" target="_blank">{{ quiz.newsLink }}</a>
            </div>
            <div class="explanation">
              <h3>해설</h3>
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
  
  const route = useRoute();
  const router = useRouter();
  const quizId = route.params.quizId;
  const quiz = ref({});
  
  onMounted(async () => {
    try {
      const userId = localStorage.getItem('userId');
      const response = await axios.post('/solved/find', {
        userId: userId,
        quizId: quizId,
      });
      quiz.value = response.data;
    } catch (error) {
      console.error(error);
    }
  });
  
  const goBack = () => {
    router.go(-1);
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .quiz-container {
    display: flex;
    justify-content: space-between;
  }
  
  .quiz-content {
    flex: 1;
    margin-right: 20px;
  }
  
  .quiz-question {
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .options {
    list-style-type: none;
    padding: 0;
  }
  
  .options li {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .option-label {
    display: inline-block;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background-color: #ccc;
    color: #fff;
    text-align: center;
    margin-right: 10px;
  }
  
  .option-label.correct {
    background-color: green;
  }
  
  .option-label.incorrect {
    background-color: red;
  }
  
  .explanation-container {
    flex: 1;
  }
  
  .explanation,
  .news-link {
    margin-bottom: 20px;
  }
  
  .back-button {
    background-color: #f0f0f0;
    color: #333;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    border-radius: 4px;
  }
  
  .back-button:hover {
    background-color: #e0e0e0;
  }
  </style>