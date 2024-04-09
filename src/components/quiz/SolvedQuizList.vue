<template>
  <div>
    <Header :isLoggedIn="true"></Header>
    <div class="container">
      <h2>풀었던 문제 확인</h2>
      <div class="date-picker">
        <VueDatePicker v-model="selectedDate" format="yyyy-MM-dd" @update:model-value="fetchSolvedQuizList"></VueDatePicker>
      </div>
      <p v-if="noQuizMessage" class="no-quiz-message">{{ noQuizMessage }}</p>
      <div class="quiz-list" v-else>
        <div class="quiz-item" v-for="solved in solvedList" :key="solved.id" @click="goToQuizDetail(solved.quizId)">
          {{ solved.content }}
        </div>
      </div>
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
const solvedList = ref([]);
const noQuizMessage = ref('');

onMounted(fetchSolvedQuizList);

async function fetchSolvedQuizList() {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.post('/solved/find/all', {
      userId: userId,
      solvedDate: formatDate(selectedDate.value),
    });
    solvedList.value = response.data;
    noQuizMessage.value = solvedList.value.length === 0 ? '풀었던 문제가 없습니다.' : '';
  } catch (error) {
    console.error(error);
  }
}

function formatDate(date) {
  return date.toISOString().split('T')[0];
}

const goToQuizDetail = (quizId) => {
  router.push(`/solved-quiz/${quizId}`);
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
}
</style>