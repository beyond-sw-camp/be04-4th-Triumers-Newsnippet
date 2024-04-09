<template>
    <input id="dateInput" type="date" v-model="date" />

    <div v-if="crawlingQuizList" class="crawlingQuiz-container">
        <template v-for="(crawlingQuiz, index) in crawlingQuizList" :key="crawlingQuiz.id">

            <div class="crawlingQuiz-item">
                <div class="clickDiv" data-bs-toggle="collapse" :data-bs-target="`#crawling${crawlingQuiz.id}`"
                    :aria-controls="`#crawling${crawlingQuiz.id}`">
                    <div id="news-category">
                        {{ crawlingQuiz.category.categoryName }}
                    </div>
                    <p id="question"> {{ crawlingQuiz.content }} </p>
                </div>

                <div id="selectBtn" @click.stop="changeSelect(crawlingQuiz.id, index)"  :class="{selected: crawlingQuiz.selected, notSelected: !crawlingQuiz.selected}">
                    <p id="selected-text" v-if="crawlingQuiz.selected">출제</p>
                    <p id="notSelected-text" v-else>미출제</p>
                </div>
            </div>

            <div class="collapse" :id="`crawling${crawlingQuiz.id}`">
                <div>
                    <p id="content">{{ crawlingQuiz.content }}</p>
                    <p> <span class="option">A</span> <span class="optionContent"> {{ crawlingQuiz.optionA }} </span> </p>
                    <p> <span class="option">B</span> <span class="optionContent"> {{ crawlingQuiz.optionB }} </span> </p>
                    <p> <span class="option">C</span> <span class="optionContent"> {{ crawlingQuiz.optionC }} </span> </p>
                    <p> <span class="option">D</span> <span class="optionContent"> {{ crawlingQuiz.optionD }} </span> </p>
                </div>
                <hr>
                <div>
                    <p> <span><a :href="`${crawlingQuiz.newsLink}`">기사 링크</a></span> </p>
                    <p>
                        {{ crawlingQuiz.explanation }}
                    </p>
                </div>

            </div>
        </template>
    </div>
</template>


<script setup>
import { ref, onMounted, watch } from "vue";

const date = ref('');
const crawlingQuizList = ref(null);

onMounted(async () => {

    const getDate = () => {
        const today = new Date;
        const year = today.getFullYear();
        const month = ('0' + (today.getMonth() + 1)).slice(-2);
        const day = ('0' + today.getDate()).slice(-2);

        return year + "-" + month + "-" + day;
    }
    date.value = getDate();
    getCrawlingQuizListByDate(date.value);
});

async function getCrawlingQuizListByDate(date) {
    const response = fetch('http://localhost:8555/manage/findCrawlingQuiz', {
        method: "POST", headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            date: date
        })
    }).then(response => response.json());
    const data = await response;
    crawlingQuizList.value = data;
}

watch(date, (newValue, oldValue) => {
    if (newValue !== oldValue) {
        getCrawlingQuizListByDate(newValue);
    }
});

async function changeSelect(id, index) {

    const isSelected = !crawlingQuizList.value[index].selected;

    if (isSelected)
        addQuiz(id)
    else
        deleteQuiz(id);

    crawlingQuizList.value[index].selected = isSelected;
}

async function addQuiz(id) {
    const response = fetch(`http://localhost:8555/manage/addQuiz/${id}`).then(response => response.json());
    const data = await response;
}

async function deleteQuiz(id) {
    const response = fetch(`http://localhost:8555/manage/deleteQuiz/${id}`, {
        method: "DELETE"
    }).then(response => response.json());
    const data = await response;
}

</script>

<style scoped>
@import url('@/assets/css/manage/QuizList.css');
</style>
