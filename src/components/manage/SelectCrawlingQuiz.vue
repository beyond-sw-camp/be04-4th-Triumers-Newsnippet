<template>
    <input id="dateInput" type="date" v-model="date" @update:model-value="getCrawlingQuizListByDate" />

    <div v-if="$store.state.isLoading" class="loading-spinner">
        <div class="spinner"></div>
        <!-- 로딩 스피너 또는 로딩 표시 -->
    </div>
    <div v-else-if="crawlingQuizList" class="crawlingQuiz-container">
        <template v-for="crawlingQuiz in paginatedQuizList" :key="crawlingQuiz.id">
            <div class="crawlingQuiz-item">
                <div class="clickDiv" data-bs-toggle="collapse" :data-bs-target="`#crawling${crawlingQuiz.id}`"
                    :aria-controls="`#crawling${crawlingQuiz.id}`">
                    <div id="news-category" :style="{ backgroundColor: getCategoryColor(crawlingQuiz.category.id) }">
                        {{ crawlingQuiz.category.categoryName }}
                    </div>
                    <p id="question"> {{ crawlingQuiz.content }} </p>
                </div>

                <div id="selectBtn" @click.stop="changeSelect(crawlingQuiz.id, crawlingQuiz.selected)"
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

        <div class="pagination">
            <button @click="goToFirstPage" :disabled="currentPage === 1">&lt;&lt;</button>
            <button @click="previousPageGroup" :disabled="currentPage === 1">&lt;</button>
            <template v-for="pageNumber in visiblePageNumbers" :key="pageNumber">
                <button @click="goToPage(pageNumber)" :class="{ active: pageNumber === currentPage }">
                    {{ pageNumber }}
                </button>
            </template>
            <button @click="nextPageGroup" :disabled="currentPage === totalPages">&gt;</button>
            <button @click="goToLastPage" :disabled="currentPage === totalPages">&gt;&gt;</button>
        </div>
    </div>
    <div v-else class="crawlingQuiz-container">
        <div id="noData-div">
            <p id="noData">문제 데이터가 없습니다.</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import axios from 'axios';
import { useStore } from 'vuex';

const store = useStore();

const date = ref('');
const crawlingQuizList = ref(null);

const currentPage = ref(1);
const pageSize = 10;
const pageGroupSize = 10;

const paginatedQuizList = computed(() => {
    const startIndex = (currentPage.value - 1) * pageSize;
    const endIndex = startIndex + pageSize;
    return crawlingQuizList.value ? crawlingQuizList.value.slice(startIndex, endIndex) : [];
});

const totalPages = computed(() => {
    return crawlingQuizList.value ? Math.ceil(crawlingQuizList.value.length / pageSize) : 0;
});

const visiblePageNumbers = computed(() => {
    const startPage = Math.floor((currentPage.value - 1) / pageGroupSize) * pageGroupSize + 1;
    const endPage = Math.min(startPage + pageGroupSize - 1, totalPages.value);
    return Array(endPage - startPage + 1)
        .fill()
        .map((_, index) => startPage + index);
});

onMounted(async () => {
    const getDate = () => {
        const today = new Date();
        const year = today.getFullYear();
        const month = ('0' + (today.getMonth() + 1)).slice(-2);
        const day = ('0' + today.getDate()).slice(-2);
        return year + "-" + month + "-" + day;
    };
    date.value = getDate();
    await getCrawlingQuizListByDate();
});

async function getCrawlingQuizListByDate() {
    store.commit('setLoading', true);
    try {
        const token = localStorage.getItem('token');
        if (token) {
            axios.defaults.headers.common['Authorization'] = token;
            const response = await axios.post('http://localhost:7777/manage/findCrawlingQuiz', { date: date.value });
            crawlingQuizList.value = response.data;
        } else {
            alert("잘못된 접근입니다.");
        }
    } catch (error) {
        crawlingQuizList.value = null;
    } finally {
        store.commit('setLoading', false);
    }
}

async function changeSelect(id, isSelected) {
    if (isSelected) {
        await deleteQuiz(id);
    } else {
        await addQuiz(id);
    }
    await getCrawlingQuizListByDate();
}

async function addQuiz(id) {
    try {
        const token = localStorage.getItem('token');
        if (token) {
            axios.defaults.headers.common['Authorization'] = token;
            await axios.get(`http://localhost:7777/manage/addQuiz/${id}`);
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
            await axios.delete(`http://localhost:7777/manage/deleteQuiz/${id}`);
        } else {
            alert("잘못된 접근입니다.");
        }
    } catch (error) {
        alert("문제 삭제에 실패했습니다.");
    }
}

function goToFirstPage() {
    currentPage.value = 1;
}

function goToLastPage() {
    currentPage.value = totalPages.value;
}

function previousPageGroup() {
    currentPage.value = Math.max(currentPage.value - pageGroupSize, 1);
}

function nextPageGroup() {
    currentPage.value = Math.min(currentPage.value + pageGroupSize, totalPages.value);
}

function goToPage(pageNumber) {
    currentPage.value = pageNumber;
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