import {createRouter, createWebHistory} from 'vue-router';

import MainView from '@/views/MainView.vue';

import ManageQuizView from '@/views/ManageQuizView.vue';
import SelectCrawlingQuiz from '@/components/manage/SelectCrawlingQuiz.vue';
import SelectedQuiz from '@/components/manage/SelectedQuiz.vue';
import SignUp from '@/components/user/SignUp.vue';
import Login from '@/components/user/Login.vue';
import MyPage from '@/components/user/MyPage.vue';
import EditMyInfo from '@/components/user/EditMyInfo.vue';
import EditPassword from '@/components/user/EditPassword.vue';
import League from '@/components/quiz/League.vue';
import TodayQuiz from '@/components/quiz/TodayQuiz.vue';
import SolvedQuizList from '@/components/quiz/SolvedQuizList.vue';
import SolvedQuizDetail from '@/components/quiz/SolvedQuizDetail.vue';
import PolicyService from '@/components/policy/service.vue';
import PolicyPrivacy from '@/components/policy/privacy.vue';
import PolicyAbout from '@/components/policy/about.vue';



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
            path: '/my-page',
            component: MyPage,
        },
        {
            path: '/edit-my-info',
            component: EditMyInfo,
        },
        {
            path: '/edit-my-password',
            component: EditPassword,
        },
        {
            path: '/today-quiz',
            component: TodayQuiz,
        },
        {
            path: '/solved-quiz-list',
            component: SolvedQuizList,
        },
        {
            path: '/solved-quiz-detail/:id',
            component: SolvedQuizDetail,
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
                    path: '/manage/selectQuiz',
                    component: SelectCrawlingQuiz
                },
                {
                    path: '/manage/selectedQuiz',
                    component: SelectedQuiz
                }
            ]
        },
        {
            path: '/policy-service',
            component: PolicyService
        },
        {
            path: '/policy-privacy',
            component: PolicyPrivacy,
        },
        {
            path: '/policy-about',
            component: PolicyAbout,
        }
    ]
});

export default router;