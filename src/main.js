
import { createApp } from 'vue'
import router from './router/mainRouter.js'
import App from './App.vue'

// npm install vue bootstrap bootstrap-vue-3
// npm install axios

const app = createApp(App)
app.use(router)
app.mount('#app')