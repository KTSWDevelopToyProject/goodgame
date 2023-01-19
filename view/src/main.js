import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";

import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.config.globalProperties.$axios = axios;
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')
