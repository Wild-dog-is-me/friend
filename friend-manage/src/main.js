import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'

import './assets/main.css'
// 在main.js里引入：
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'


const app = createApp(App)
app.use(ElementPlus, {
  locale: zhCn,
})
app.use(ElementPlus)
app.use(createPinia())
app.use(router)

app.mount('#app')
