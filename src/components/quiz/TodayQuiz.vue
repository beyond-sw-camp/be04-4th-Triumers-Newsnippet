<template>
  <div>
    <Header :isLoggedIn="true"></Header>
    <div class="quiz-container">
      <div v-if="currentStep === 1">
        <!-- 문제 및 선택지 표시 -->
        <div class="quiz-info">
          <span class="date">{{ currentQuiz.date }}</span>
          <span class="category"> {{ currentQuiz.categoryName }}</span>
          <span class="accuracy">정답률: {{ (currentQuiz.correctRate * 100).toFixed(2) }}%</span>
        </div>
        <h2 class="question">{{ currentQuiz.no }}. {{ currentQuiz.content }}</h2>
        <div class="options">
          <div class="option" :class="{ 'selected': selectedOption === 'A' }" @click="selectOption('A')">
            A. {{ currentQuiz.optionA }}
          </div>
          <div class="option" :class="{ 'selected': selectedOption === 'B' }" @click="selectOption('B')">
            B. {{ currentQuiz.optionB }}
          </div>
          <div class="option" :class="{ 'selected': selectedOption === 'C' }" @click="selectOption('C')">
            C. {{ currentQuiz.optionC }}
          </div>
          <div class="option" :class="{ 'selected': selectedOption === 'D' }" @click="selectOption('D')">
            D. {{ currentQuiz.optionD }}
          </div>
        </div>
        <button @click="nextStep" :disabled="!selectedOption" class="next-btn">다음</button>
        <div class="progress">
          <span class="progress-text">진행률:</span>
          <span class="progress-bar" :style="{ width: `${currentQuizIndex + 1 / quizzes.length * 100}%` }"></span>
          {{ currentQuizIndex + 1 }}/{{ quizzes.length }}
        </div>
      </div>

      <div v-if="currentStep === 2">
        <!-- 정답 확인 및 해설 표시 -->
        <h2 class="question">{{ currentQuiz.no }}. {{ currentQuiz.content }}</h2>
        <div class="result">
          <p v-if="isCorrect" class="correct">정답입니다!</p>
          <p v-else class="incorrect">오답입니다.</p>
        </div>
        <div class="options">
          <div class="option"
            :class="{ correct: ((selectedOption === 'A' && isCorrect) || currentQuiz.answer == 'A'), incorrect: selectedOption === 'A' && !isCorrect }">
            A. {{ currentQuiz.optionA }}
            <span v-if="currentQuiz.optionA === currentQuiz.answer" class="answer-label">(정답)</span>
          </div>
          <div class="option"
            :class="{ correct: (selectedOption === 'B' && isCorrect) || currentQuiz.answer == 'B', incorrect: selectedOption === 'B' && !isCorrect }">
            B. {{ currentQuiz.optionB }}
            <span v-if="currentQuiz.optionB === currentQuiz.answer" class="answer-label">(정답)</span>
          </div>
          <div class="option"
            :class="{ correct: (selectedOption === 'C' && isCorrect) || currentQuiz.answer == 'C', incorrect: selectedOption === 'C' && !isCorrect }">
            C. {{ currentQuiz.optionC }}
            <span v-if="currentQuiz.optionC === currentQuiz.answer" class="answer-label">(정답)</span>
          </div>
          <div class="option"
            :class="{ correct: (selectedOption === 'D' && isCorrect) || currentQuiz.answer == 'D', incorrect: selectedOption === 'D' && !isCorrect }">
            D. {{ currentQuiz.optionD }}
            <span v-if="currentQuiz.optionD === currentQuiz.answer" class="answer-label">(정답)</span>
          </div>
        </div>
        <p class="explanation">해설: {{ currentQuiz.explanation }}</p>
        <a :href="currentQuiz.newsLink" target="_blank" class="source-link">원문 링크</a>
        <button @click="nextQuestion" class="next-btn">다음 문제</button>
        <div class="progress">
          <span class="progress-text">진행률:</span>
          <span class="progress-bar" :style="{ width: `${(currentQuizIndex + 1) / quizzes.length * 100}%` }"></span>
          {{ currentQuizIndex + 1 }}/{{ quizzes.length }}
        </div>
      </div>

      <div v-if="currentStep === 3">
        <!-- 최종 결과 표시 -->
        <div class="result-step">
          <h2 class="result-title">🎉결과🎉</h2>
          <hr>
          <div id="result-text">
            <h4 class="totalCount"> {{ correctCount }} / {{ quizzes.length }} </h4>
            <p class="correctCount">맞힌 문제 수: {{ correctCount }}</p>
            <p class="incorrectCount">틀린 문제 수: {{ quizzes.length - correctCount }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';
import Header from '@/views/Header.vue';

const quizzes = ref([]);
const currentQuizIndex = ref(0);
const currentQuiz = reactive({});
const selectedOption = ref(null);
const correctCount = ref(0);
const currentStep = ref(1);
const isCorrect = ref(false);
// const userId = ref(null); // 사용자 ID를 가져오는 로직이 필요합니다.
// 백엔드 db 연결 테스트를 위해 임의로 할당
const userId = ref(1);

const fetchQuizzes = async () => {

  try {
    const token = localStorage.getItem('token');
    if (token) {
      axios.defaults.headers.common['Authorization'] = token;
      const response = await axios.post('http://localhost:7777/quiz/test', { date: new Date() });
      quizzes.value = response.data;

      setCurrentQuiz();

    } else {
      alert("잘못된 접근입니다.");
    }
  } catch (error) {
    console.error('문제 데이터 가져오기 실패:', error);
  }
};

const setCurrentQuiz = () =>{
    currentQuiz.id = quizzes.value[currentQuizIndex.value].id;
    currentQuiz.date = quizzes.value[currentQuizIndex.value].date;
    currentQuiz.no = quizzes.value[currentQuizIndex.value].no;
    currentQuiz.categoryName = quizzes.value[currentQuizIndex.value].categoryName;
    currentQuiz.content = quizzes.value[currentQuizIndex.value].content;
    currentQuiz.optionA = quizzes.value[currentQuizIndex.value].optionA;
    currentQuiz.optionB = quizzes.value[currentQuizIndex.value].optionB;
    currentQuiz.optionC = quizzes.value[currentQuizIndex.value].optionC;
    currentQuiz.optionD = quizzes.value[currentQuizIndex.value].optionD;
    currentQuiz.solvedCnt = quizzes.value[currentQuizIndex.value].solvedCnt;
    currentQuiz.correctCnt = quizzes.value[currentQuizIndex.value].correctCnt;
    currentQuiz.correctRate = quizzes.value[currentQuizIndex.value].correctRate;
}

const checkAnswerCorrectness = async () => {

  try {
    const token = localStorage.getItem('token');
    if (token) {
      axios.defaults.headers.common['Authorization'] = token;
      const response = await axios.post('http://localhost:7777/solved/check',
        {
          userId: userId.value,
          quizId: currentQuiz.id,
          selectedOption: selectedOption.value
        }
      );
      const data = response.data;
      isCorrect.value = data.correct;
      if (isCorrect.value) {
        correctCount.value++;
      }
    } else {
      alert("잘못된 접근입니다.");
    }
  } catch (error) {
    console.error('정답 확인 및 저장 실패:', error);
  }
};


const selectOption = (option) => {
  selectedOption.value = option;
};

const nextStep = async () => {
  if (currentStep.value === 1) {
    await checkAnswerCorrectness();
    // 두 번째 페이지로 넘어갈 때 정답, 해설, 원문 링크 정보 설정
    currentQuiz.answer = quizzes.value[currentQuizIndex.value].answer;
    currentQuiz.explanation = quizzes.value[currentQuizIndex.value].explanation;
    currentQuiz.newsLink = quizzes.value[currentQuizIndex.value].newsLink;
    currentStep.value = 2;
  }
};

const nextQuestion = async () => {
  if (currentQuizIndex.value < quizzes.value.length - 1) {
    currentQuizIndex.value++;
    setCurrentQuiz();
    selectedOption.value = null;
    currentStep.value = 1;
  } else {
    currentStep.value = 3;
  }
};

onMounted(() => {
  fetchQuizzes();
});
</script>

<style scoped>
@import url('@/styles/quiz/TodayQuiz.css');
</style>