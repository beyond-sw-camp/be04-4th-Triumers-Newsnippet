<template>
    <input id="dateInput" type="date" v-model="date" @update:model-value="getCrawlingQuizListByDate" />

    <div v-if="crawlingQuizList && !isLoading" class="crawlingQuiz-container">
        <template v-for="(crawlingQuiz, index) in pageQuizList" :key="crawlingQuiz.id">

            <div class="crawlingQuiz-item">
                <div class="clickDiv" data-bs-toggle="collapse" :data-bs-target="`#crawling${crawlingQuiz.id}`"
                    :aria-controls="`#crawling${crawlingQuiz.id}`">
                    <div id="news-category" :style="{ backgroundColor: getCategoryColor(crawlingQuiz.category.id) }">
                        {{ crawlingQuiz.category.categoryName }}
                    </div>
                    <p id="question"> {{ crawlingQuiz.content }} </p>
                </div>

                <div id="selectBtn" @click.stop="changeSelect(crawlingQuiz.id, index)"
                    :class="{ selected: crawlingQuiz.selected, notSelected: !crawlingQuiz.selected }">
                    <p id="selected-text" v-if="crawlingQuiz.selected">출제</p>
                    <p id="notSelected-text" v-else>미출제</p>
                </div>
            </div>

            <div class="collapse" :id="`crawling${crawlingQuiz.id}`">
                <div>
                    <p id="content">{{ crawlingQuiz.content }}</p>
                    <p> <span class="option" :class="{ correct: crawlingQuiz.answer == 'A' }">A</span> <span
                            class="optionContent"> {{ crawlingQuiz.optionA }} </span></p>
                    <p> <span class="option" :class="{ correct: crawlingQuiz.answer == 'B' }">B</span> <span
                            class="optionContent"> {{ crawlingQuiz.optionB }} </span></p>
                    <p> <span class="option" :class="{ correct: crawlingQuiz.answer == 'C' }">C</span> <span
                            class="optionContent"> {{ crawlingQuiz.optionC }} </span></p>
                    <p> <span class="option" :class="{ correct: crawlingQuiz.answer == 'D' }">D</span> <span
                            class="optionContent"> {{ crawlingQuiz.optionD }} </span></p>
                </div>
                <hr>
                <div>
                    <p> <span><a :href="`${crawlingQuiz.newsLink}`">원본 링크</a></span> </p>
                    <p>
                        {{ crawlingQuiz.explanation }}
                    </p>
                </div>

            </div>
        </template>
        <ul class="pagination justify-content-center">
            <template v-for="num in totalPage">
            <li class="page-item" @click="changePage(num)">
                <a class="page-link">{{ num }}</a>
            </li>
            </template>
        </ul>
    </div>
    <div v-else class="crawlingQuiz-container">
        <div id="noData-div">
            <p id="noData">문제 데이터가 없습니다.</p>
        </div>
    </div>


</template>


<script setup>
import { ref, onMounted } from "vue";
import axios from 'axios';

const date = ref('');
const crawlingQuizList = ref(null);
const pageQuizList = ref([]);
const isLoading = ref(false);
const totalPage = ref(1);
const pageLength = 5;

onMounted(async () => {

    const getDate = () => {
        const today = new Date;
        const year = today.getFullYear();
        const month = ('0' + (today.getMonth() + 1)).slice(-2);
        const day = ('0' + today.getDate()).slice(-2);

        return year + "-" + month + "-" + day;
    }
    date.value = getDate();
    await getCrawlingQuizListByDate();
});

async function getCrawlingQuizListByDate() {
    isLoading.value = true;
    try {
        const token = localStorage.getItem('token');
        if (token) {
            axios.defaults.headers.common['Authorization'] = token;
            const response = await axios.post('http://localhost:7777/manage/findCrawlingQuiz', { date: date.value });
            crawlingQuizList.value = response.data;

            totalPage.value = Math.ceil(crawlingQuizList.value.length / pageLength);
            await changePage(1);
        } else {
            alert("잘못된 접근입니다.");
        }
    } catch (error) {
        crawlingQuizList.value = null;
    } finally {
        isLoading.value = false;
    }
    
}

async function changePage(page) {
    pageQuizList.value = crawlingQuizList.value.slice((page-1)*pageLength, page*pageLength);
}

async function changeSelect(id, index) {

    const isSelected = !pageQuizList.value[index].selected;

    if (isSelected)
        addQuiz(id)
    else
        deleteQuiz(id);

        pageQuizList.value[index].selected = isSelected;
}

async function addQuiz(id) {
    try {
        const token = localStorage.getItem('token');
        if (token) {
            axios.defaults.headers.common['Authorization'] = token;
            const response = await axios.get(`http://localhost:7777/manage/addQuiz/${id}`);
        } else {
            alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert("문제 출제에 실패했습니다.");
    }
}

async function deleteQuiz(id) {
    try {
        const token = localStorage.getItem('token');
        if (token) {
            axios.defaults.headers.common['Authorization'] = token;
            const response = await axios.delete(`http://localhost:7777/manage/deleteQuiz/${id}`);
        } else {
            alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert("문제 삭제에 실패했습니다.");
    }
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
@import url('@/styles/manage/QuizList.css');
</style>
