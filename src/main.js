
import { createApp } from 'vue'
import router from './router/mainRouter.js'
import App from './App.vue'
import axios from 'axios';

// loading 
import ElementLoading from 'vue-element-loading';

import store from './store';
// 글로벌 CSS 파일 import
import './styles/global.css';

axios.defaults.baseURL = 'http://localhost:5173'; // 백엔드 서버의 주소와 포트를 지정합니다.
axios.defaults.withCredentials = true; // 쿠키를 포함하여 요청을 보내도록 설정합니다.


// npm install vue bootstrap bootstrap-vue-3
// npm install axios
// npm install @vuepic/vue-datepicker
// npm install vuex
// npm install vue-element-loading

// npm install vue bootstrap-vue-3
import BootstrapVue3 from 'bootstrap-vue-3'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

const app = createApp(App);
app.component('element-loading', ElementLoading);
app.use(router);
app.use(store);
app.use(BootstrapVue3);
app.mount('#app');
