<template>

    <input id="dateInput" type="date" v-model="date"/>

    <div v-if="crawlingQuizList" class="crawlingQuiz-container">
        <template v-for="crawlingQuiz in crawlingQuizList" :key="crawlingQuiz.id">

            <div class="crawlingQuiz-item">
                <div class="clickDiv" data-bs-toggle="collapse" :data-bs-target="`#crawling${crawlingQuiz.id}`"
                    :aria-controls="`#crawling${crawlingQuiz.id}`">
                    <div id="news-category">
                        {{ crawlingQuiz.category.categoryName }}
                    </div>
                    <p id="question"> {{ crawlingQuiz.content }} </p>
                </div>

                <div class="selectBtn" @click.stop="changeSelect(crawlingQuiz.id)">
                    <p id="select-text" v-if="crawlingQuiz.selected">출제</p>
                    <p id="select-text" v-else>미출제</p>
                </div>
            </div>

            <div class="collapse" :id="`crawling${crawlingQuiz.id}`">
                <div>
                    <p id="content">{{ crawlingQuiz.content }}</p>
                    <p> <span class="option">A</span> <span> {{ crawlingQuiz.optionA }} </span> </p>
                    <p> <span class="option">B</span> <span> {{ crawlingQuiz.optionB }} </span> </p>
                    <p> <span class="option">C</span> <span> {{ crawlingQuiz.optionC }} </span> </p>
                    <p> <span class="option">D</span> <span> {{ crawlingQuiz.optionD }} </span> </p>
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
import { ref, onMounted, reactive } from "vue";

const date = ref(0);

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
    getCrowdQuizListByDate(date);
});

async function getCrowdQuizListByDate(date){
    const response = fetch('http://localhost:8555/manage/findCrawlingQuiz', {
        method: "POST", headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            date: date.value
        })
    }).then(response => response.json());
    const data = await response;
    crawlingQuizList.value = data;
}



const changeSelect = (id) => {

    const isSelected = !crawlingQuizList[id].selected;
    let url = '';

    if (isSelected) {
        url = `http://localhost:8080/manage/addQuiz/${id}`;
    }
    else {
        url = `http://localhost:8080/manage/deleteQuiz/${id}`;
    }

    // const res = await fetch(url);

    crawlingQuizList[id].selected = isSelected;
}

</script>

<style scoped>
@import url('@/assets/css/manage/QuizList.css');
</style>
