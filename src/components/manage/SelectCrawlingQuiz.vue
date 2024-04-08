<template>

    <input type="date" v-model="date" />

    <div class="crawlingQuiz-container">
        <template v-for="crawlingQuiz in crawlingQuizList" :key="crawlingQuiz.id">

            <div class="crawlingQuiz-item">
                <div class="click-div" data-bs-toggle="collapse" :data-bs-target="`#crawling${crawlingQuiz.id}`"
                    :aria-controls="`#crawling${crawlingQuiz.id}`">
                    <div id="news-category">
                        카테고리
                    </div>
                    <p id="question"> {{ crawlingQuiz.content }} </p>
                </div>

                <div class="selectBtn" @click.stop="changeSelect(crawlingQuiz.id - 1)">
                    <p id="select-text" v-if="crawlingQuiz.selected">출제</p>
                    <p id="select-text" v-else>미출제</p>
                </div>
            </div>

            <div class="collapse" :id="`crawling${crawlingQuiz.id}`">
                <div>
                    <p id="content">{{ crawlingQuiz.content }}</p>
                    <p> <span class="option">A</span> <span> 보기 </span> </p>
                    <p> <span class="option">B</span> <span> 보기 </span> </p>
                    <p> <span class="option">C</span> <span> 보기 </span> </p>
                    <p> <span class="option">D</span> <span> 보기 </span> </p>
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

<style scoped>
.crawlingQuiz-container {
    padding: 20px;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

.crawlingQuiz-item {
    width: 800px;
    margin: 5px;
    padding: 10px;
    border-radius: 10px;
    background-color: #F4F3F6;
    display: flex;
    justify-content: center;
}

#news-category {
    width: 100px;
    margin: 20px 10px;
    padding-top: 5px;
    background-color: #D9D9D9;
    border-radius: 50px;
    text-align: center;
}

.click-div{
    display: flex;
}

.selectBtn {
    width: 100px;
    margin: 10px;
    padding-top: 10px;
    border-radius: 10px;
    text-align: center;
    background-color: white;
    justify-content: flex-end;
}

#question {
    width: 550px;
    margin: 10px;
}

.collapse,
.collapsing {
    width: 800px;
    background-color: beige;
    padding: 20px;
}
</style>

<script setup>
import { ref, onMounted, reactive } from "vue";
import { useRoute } from 'vue-router'

const date = ref(0);

const changeSelect = (id) => {

    // 삭제인지 추가인지 판단해서 요청보내기, 버튼 변경
    const isSelected = crawlingQuizList[id].selected;
    let url = '';

    if (isSelected) {
        url = `http://#{id}`;
    }
    else {
        url = `http://#{id}`;
    }

    // const res = await fetch(url);

    crawlingQuizList[id].selected = !isSelected;

}

const crawlingQuizList = reactive(
    [
        {
            "id": 1,
            "content": "EELS 로봇에 관한 다음 설명 중 옳은 것은 무엇입니까?",
            "optionA": "EELS 로봇은 지구의 앨버타주에 있는 애서배스카 빙하에서 개발되었습니다.",
            "optionB": "이 로봇은 머리 쪽에 자율주행용 라이다와 카메라를 장착하고 있어서 스스로 움직일 수 있습니다.",
            "optionC": "EELS 로봇의 목표는 타이탄 위성에서의 탐사를 위한 것입니다.",
            "optionD": "이 로봇은 무게가 50kg이며, 액추에이터는 총 24개 달려 있습니다.",
            "answer": "C",
            "explanation": "캐나다 앨버타주의 애서배스카 빙하에서 출발한 NASA의 로봇 탐사 임무에 대한 내용을 담고 있습니다. 이 임무는 미 항공우주국이 개발 중인 외계 생명체 탐사로봇인 EELS(일스)를 사용하여 토성의 위성 엔셀라두스에 보내는 것이 목표입니다. 이 로봇은 지구의 극한 환경에서도 작동할 수 있는 고성능을 갖추고 있으며, 엔셀라두스의 얼음 아래에 있는 바다에서 생명체를 찾는 임무를 수행할 예정입니다.",
            "newsLink": "https://www.ytn.co.kr/_ln/0105_202404012353120871",
            "newsDate": "2024-04-02",
            "category": {
                "id": 9,
                "categoryName": "IT/과학"
            },
            "selected": true
        },
        {
            "id": 2,
            "content": "중국 전자상거래 기업 알리바바가 1시간 이내에 전 세계로 상품을 배송하는 시도에 나선다고 하는데, 이를 위해 협업하는 로켓 개발 스타트업은?",
            "optionA": "스페이스 엑스 (Space X)",
            "optionB": "스페이스 에포크 (Space Epoch)",
            "optionC": "블루 오리진 (Blue Origin)",
            "optionD": "로켓랩 (Rocket Lab)",
            "answer": "B",
            "explanation": "알리바바가 전 세계 1시간 이내 배송을 추진하기 위해 협업하는 로켓 개발 스타트업은 스페이스 에포크입니다. 이 소식은 2024년 4월 2일에 보도되었습니다. 이는 알리바바의 전 세계적인 물류 서비스를 더욱 확장하기 위한 시도 중 하나로, 스페이스 에포크의 재사용 로켓 XZY-1을 활용하여 1시간 이내에 상품을 운송할 계획입니다.",
            "newsLink": "https://www.ytn.co.kr/_ln/0104_202404021429256706",
            "newsDate": "2024-04-02",
            "category": {
                "id": 9,
                "categoryName": "IT/과학"
            },
            "selected": false
        }
    ]
);
</script>