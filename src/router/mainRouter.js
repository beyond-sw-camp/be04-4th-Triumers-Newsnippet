import {createRouter, createWebHistory} from 'vue-router';

import MainView from '@/views/MainView.vue';

import ManageQuizView from '@/views/ManageQuizView.vue';
import SelectCrawlingQuiz from '@/components/manage/SelectCrawlingQuiz.vue';
import SelectedQuiz from '@/components/manage/SelectedQuiz.vue';
import SignUp from '@/components/user/SignUp.vue';
import Login from '@/components/user/Login.vue';
import League from '@/components/quiz/League.vue';
import TodayQuiz from '@/components/quiz/TodayQuiz.vue';
import SolvedQuiz from '@/components/quiz/SolvedQuiz.vue';


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: MainView
        },
        {
            path: '/signup',
            component: SignUp,
        },
        {
            path: '/login',
            component: Login,
        },
        {
            path: '/today-quiz',
            component: TodayQuiz,
        },
        {
            path: '/solved-quiz',
            component: SolvedQuiz,
        },
        {
            path: '/league',
            component: League,
        },
        {
            path: '/manage',
            component: ManageQuizView,
            children:[
                {
                    path: '/selectQuiz',
                    component: SelectCrawlingQuiz
                },
                {
                    path: '/selectedQuiz',
                    component: SelectedQuiz
                }
            ]
        }
    ]
});

export default router;