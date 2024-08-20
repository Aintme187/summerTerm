import {createApp} from 'vue'
import ElementPlus, {dayjs} from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import {router} from "@/router/index.js";
import {zhCn} from "element-plus/es/locale/index";
import store from "@/store/index.js";
import {formatTime} from "@/utils/time.js";
import lodash from 'lodash'
import mavonEditor from "mavon-editor";
import 'mavon-editor/dist/css/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'  // 引入Element Plus icon 所需
import { createPinia } from 'pinia'
import persist from 'pinia-plugin-persistedstate'

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(ElementPlus, {locale: zhCn})
app.use(createPinia().use(persist))
app.use(router)
app.use(store)
app.use(mavonEditor)
// 注册全局指令
app.directive('title', {
    mounted(el, binding) {
        document.title = el.dataset.title;
    }
});

// 注册全局过滤器
app.config.globalProperties.$filterDateTime = function(dateTime) {
    return dayjs(dateTime).format('YYYY-MM-DD HH:mm:ss');
};

// 全局混入 lodash
app.config.globalProperties.$_ = lodash;

app.mount('#app')
