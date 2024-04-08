import {createRouter, createWebHistory} from 'vue-router';

import MainView from '@/views/MainView.vue';

import ManageQuizView from '@/views/ManageQuizView.vue';
import SelectCrawlingQuiz from '@/components/manage/SelectCrawlingQuiz.vue';
import SelectedQuiz from '@/components/manage/SelectedQuiz.vue';


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: MainView
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