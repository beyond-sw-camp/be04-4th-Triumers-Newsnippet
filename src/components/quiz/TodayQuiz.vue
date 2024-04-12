<template>
  <div>
    <Header :isLoggedIn="true"></Header>
    <div class="quiz-container">
      <div v-if="currentStep === 1">
        <!-- 문제 및 선택지 표시 -->
        <div class="quiz-info">
          <span class="date">{{ currentQuiz.date }}</span>
          <span class="category">카테고리: {{ currentQuiz.categoryName }}</span>
          <span class="accuracy">정답률: {{ (currentQuiz.correctCnt / currentQuiz.solvedCnt * 100).toFixed(2) }}%</span>
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
        <h2>결과</h2>
        <p>맞힌 문제 수: {{ correctCount }}</p>
        <p>틀린 문제 수: {{ quizzes.length - correctCount }}</p>
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


// 백엔드 db 연결을 위해 날짜를 2024-04-02로 고정
const fetchQuizzes = async () => {
  try {
    // const response = await axios.post('http://localhost:7777/quiz/test', { date: new Date('2024-04-02') });
    const response = fetch('http://localhost:7777/quiz/test', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        date: new Date()
      })
    }).then(response => response.json());

    const data = await response;
    quizzes.value = data;
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
    // 첫 번째 페이지에서는 정답, 해설, 원문 링크 정보를 설정하지 않음
  } catch (error) {
    console.error('문제 데이터 가져오기 실패:', error);
  }
};

const checkAnswerCorrectness = async () => {
  try {

    const response = fetch('http://localhost:7777/solved/check', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        userId: userId.value,
        quizId: currentQuiz.id,
        selectedOption: selectedOption.value
      })
    }).then(response => response.json());

    const data = await response;

    isCorrect.value = data.correct;
    if (isCorrect.value) {
      correctCount.value++;
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
.quiz-container {
  max-width: 60%;
  margin: 0 auto;
  padding: 20px;
  transition: max-width 0.5s ease;
  /* 부드러운 너비 전환 애니메이션 추가 */
}

/* 기본 스타일링은 유지하되 색상 조정 */
.next-btn {
  display: block;
  margin: 20px auto;
  padding: 10px 20px;
  background-color: #4CAF50;
  /* 기존의 #ebe4b6 대신 새로운 색상 사용 */
  border: none;
  border-radius: 5px;
  cursor: pointer;
  color: white;
  /* 텍스트 색상 변경 */
}

/* 마우스 호버 효과 추가 */
.next-btn:hover {
  background-color: #45a049;
}

/* 선택된 옵션에 대한 스타일링 추가 */
.option.selected {
  background-color: #e0e0e0;
  /* 기존의 #ebe4b6 대신 새로운 색상 사용 */
  font-weight: bold;
}

.quiz-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

/* .question {
  font-size: 1.2rem;
  margin-bottom: 20px;
} */

/* 폰트 및 텍스트 스타일 개선 */
.question {
  font-size: 1.4rem;
  /* 폰트 크기를 더 크게 조정 */
  line-height: 1.5;
  /* 줄간격을 조정하여 가독성 향상 */
}


.options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  margin-bottom: 20px;
}

.option {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
}

/* .option.selected {
  background-color: #ebe4b6;
  font-weight: bold;
} */

.option.correct {
  background-color: #d4edda;
  color: #155724;
}

.option.incorrect {
  background-color: #f8d7da;
  color: #721c24;
}

.answer-label {
  font-weight: bold;
}

.explanation {
  margin-top: 20px;
}

.source-link {
  display: block;
  margin-top: 10px;
}

.next-btn {
  display: block;
  margin: 20px auto;
  padding: 10px 20px;
  background-color: #ebe4b6;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.progress {
  display: flex;
  align-items: center;
  margin-top: 20px;
}

.progress-text {
  margin-right: 10px;
}

.progress-bar {
  height: 20px;
  background-color: #ebe4b6;
  border-radius: 10px;
  margin-right: 10px;
  flex-grow: 1;
}
</style>