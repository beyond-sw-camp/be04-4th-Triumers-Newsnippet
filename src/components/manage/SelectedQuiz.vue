<template>
    <p id="nextDate"> {{ nextDate }} 출제 문제 </p>

    <div v-if="selectedQuizList" class="crawlingQuiz-container">
        <template v-for="selectedQuiz in selectedQuizList" :key="selectedQuiz.id">

            <div class="crawlingQuiz-item">
                <div class="clickDiv" data-bs-toggle="collapse" :data-bs-target="`#crawling${selectedQuiz.id}`"
                    :aria-controls="`#crawling${selectedQuiz.id}`">
                    <div id="news-category">
                        {{ selectedQuiz.category.categoryName }}
                    </div>
                    <p id="questionSelected"> {{ selectedQuiz.content }} </p>
                </div>
            </div>

            <div class="collapse" :id="`crawling${selectedQuiz.id}`">
                <div>
                    <p id="content">{{ selectedQuiz.content }}</p>
                    <p> <span class="option">A</span> <span> 보기 </span> </p>
                    <p> <span class="option">B</span> <span> 보기 </span> </p>
                    <p> <span class="option">C</span> <span> 보기 </span> </p>
                    <p> <span class="option">D</span> <span> 보기 </span> </p>
                </div>
                <hr>
                <div>
                    <p> <span><a :href="`${selectedQuiz.newsLink}`">기사 링크</a></span> </p>
                    <p>
                        {{ selectedQuiz.explanation }}
                    </p>
                </div>

            </div>
        </template>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const nextDate = ref('');
const selectedQuizList = ref(null)

onMounted(async () => {

    const getNextDate = () => {
        const today = new Date;
        const year = today.getFullYear();
        const month = ('0' + (today.getMonth() + 1)).slice(-2);
        const day = ('0' + (today.getDate() + 1)).slice(-2);

        return year + "-" + month + "-" + day;
    }
    nextDate.value = getNextDate();
    getSelectedQuizList();
});

async function getSelectedQuizList() {
    const response = fetch('http://localhost:8555/manage/findSelectedQuiz').then(response => response.json());
    const data = await response;
    selectedQuizList.value = data;
}
</script>

<style scoped>
@import url('@/assets/css/manage/QuizList.css');
</style>