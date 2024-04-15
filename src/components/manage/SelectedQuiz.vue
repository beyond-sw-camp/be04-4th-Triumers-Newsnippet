<template>
    <span id="nextDate"> 📰 {{ nextDate }}</span>

    <div v-if="selectedQuizList" class="crawlingQuiz-container">
        <template v-for="selectedQuiz in selectedQuizList" :key="selectedQuiz.id">

            <div class="crawlingQuiz-item">
                <div class="clickDiv" data-bs-toggle="collapse" :data-bs-target="`#crawling${selectedQuiz.id}`"
                    :aria-controls="`#crawling${selectedQuiz.id}`">
                    <div id="news-category"  :style="{ backgroundColor: getCategoryColor(selectedQuiz.category.id) }">
                        {{ selectedQuiz.category.categoryName }}
                    </div>
                    <p id="questionSelected"> {{ selectedQuiz.content }} </p>
                </div>
            </div>

            <div class="collapse" :id="`crawling${selectedQuiz.id}`">
                <div>
                    <p id="content">{{ selectedQuiz.content }}</p>
                    <p> <span class="option" :class="{correct : selectedQuiz.answer == 'A'}">A</span> <span> {{selectedQuiz.optionA}} </span> </p>
                    <p> <span class="option" :class="{correct : selectedQuiz.answer == 'B'}">B</span> <span> {{selectedQuiz.optionB}} </span> </p>
                    <p> <span class="option" :class="{correct : selectedQuiz.answer == 'C'}">C</span> <span> {{selectedQuiz.optionC}} </span> </p>
                    <p> <span class="option" :class="{correct : selectedQuiz.answer == 'D'}">D</span> <span> {{selectedQuiz.optionD}} </span> </p>
                </div>
                <hr>
                <div>
                    <p> <span><a :href="`${selectedQuiz.newsLink}`">원본 링크</a></span> </p>
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
    const response = fetch('http://localhost:7777/manage/findSelectedQuiz').then(response => response.json());
    const data = await response;
    selectedQuizList.value = data;
}

const categoryColors = [
    "#89A9D6", "#93AFD9", "#90ACD9", "#9FB0D4", "#A8B4CC",
    "#A6A6C6", "#A2AACD", "#A3A5CB", "#9EA2D2", "#A5A8CB",
    "#A3A0CD", "#A8B5CF", "#8EABD5", "#808080", "#C0C0C0"
];

const getCategoryColor = (categoryId) => {
    return categoryColors[categoryId] || '#D9D9D9';
};
</script>

<style scoped>
@import url('@/assets/css/manage/QuizList.css');
</style>