import { createApp } from 'vue'
import App from './App.vue'
import './assets/css/global.css'
import './assets/css/main.css'
import './assets/css/noscript.css'
import 'font-awesome/css/font-awesome.min.css'
import router from './router/index'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(createPinia())
app.mount('#app')