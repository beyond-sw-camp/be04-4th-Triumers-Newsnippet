<template>
  <div>
    <Header :isLoggedIn="true"></Header>
    <div class="container">
      <h2>풀었던 문제 확인</h2>
      <div class="date-picker">
        <VueDatePicker v-model="selectedDate" format="yyyy-MM-dd" @update:model-value="fetchSolvedQuizList"></VueDatePicker>
      </div>
      
      <div class="quiz-list" v-if="solvedList">
        <div class="quiz-item" v-for="solved in solvedList" :key="solved.id" @click="goToQuizDetail(solved.quizId)">
          <div class="content">{{ solved.content }}</div>
          <div v-if="solved.selectedOption === solved.answer" class="correct"> O </div>
          <div v-else class="incorrect"> X </div>
        </div>
      </div>
      <p v-else class="no-quiz-message">{{ noQuizMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/views/Header.vue';
import axios from 'axios';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

const router = useRouter();
const selectedDate = ref(new Date());
const solvedList = ref(null);
const noQuizMessage = ref(null);

onMounted(fetchSolvedQuizList);

async function fetchSolvedQuizList() {
  try {
    const response = fetch('http://localhost:7777/solved/find/allByDate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        userId: 1,
        date: formatDate(selectedDate.value)
      }),
    }).then(response => response.json());
      
    const data = await response;
    solvedList.value = data;
    noQuizMessage.value = solvedList.value.length === 0 ? '풀었던 문제가 없습니다.' : '';

  } catch (error) {
    console.error(error);
  }
}

function formatDate(date) {
  return date.toISOString().split('T')[0];
}

const goToQuizDetail = (quizId) => {
  router.push(`/solved-quiz-detail/${quizId}`);
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.date-picker {
  margin-bottom: 20px;
}

.no-quiz-message {
  color: red;
  margin-bottom: 10px;
}

.quiz-list {
  list-style-type: none;
  padding: 0;
}

.quiz-item {
  background-color: #f5f5f5;
  padding: 10px;
  margin-bottom: 10px;
  cursor: pointer;
  border: 1px solid #ccc;
  border-radius: 4px;
  display: flex;
  font-size: 15px;
}

.content{
  width: 750px;
}

.correct {
    color: green;
  }
  
.incorrect {
    color: red;
  }

</style>