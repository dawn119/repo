import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
//引入根组件
import App from './App.vue'
//引入路由实例 
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//创建vue实例 
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
//安装插件 就是在vue实例上增加全局属性或方法 ...
app.use(ElementPlus, {
    locale: zhCn,
  })
app.use(createPinia())
//安装路由实现  在 选项目式api中  会增加两个实现  $route 代表路由信息  $router路由实例用于跳转
app.use(router)
//将vue实例挂载到容器id为app上 
app.mount('#app')
