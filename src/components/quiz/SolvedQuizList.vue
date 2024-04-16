<template>
  <div>
    <Header :isLoggedIn="true"></Header>
    <div v-if="$store.state.isLoading" class="loading-spinner">
        <div class="spinner"></div>
    <!-- 로딩 스피너 또는 로딩 표시 -->
    </div>
    <div v-else class="container">
      <h2>풀었던 문제 확인</h2>
      <hr>
      <div class="date-picker">
        <VueDatePicker id="datePicker" v-model="selectedDate" format="yyyy-MM-dd"
          @update:model-value="fetchSolvedQuizList">
        </VueDatePicker>
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
import { useStore } from 'vuex';

const store = useStore();

const router = useRouter();
const selectedDate = ref(new Date());
const solvedList = ref(null);
const noQuizMessage = ref(null);

onMounted(fetchSolvedQuizList);

async function fetchSolvedQuizList() {

  store.commit('setLoading', true);
  try {
    const token = localStorage.getItem('token');
    if (token) {
      axios.defaults.headers.common['Authorization'] = token;
      const response = await axios.post('http://localhost:7777/solved/find/allByDate',
        {
          solvedDate: formatDate(selectedDate.value)
        });
      solvedList.value = response.data;
      noQuizMessage.value = solvedList.value.length === 0 ? '풀었던 문제가 없습니다.' : '';
    } else {
      alert("잘못된 접근입니다.");
    }
  } catch (error) {
    console.log(error);
  } finally {
    store.commit('setLoading', false);
  }
}

function formatDate(date) {
  return date.toISOString().split('T')[0];
}

const goToQuizDetail = (quizId) => {
  router.push(`/solved-quiz-detail/${quizId}`);
}
</script>

<style scoped>
@import url('@/styles/quiz/SolvedQuizList.css');
</style>