import { createRouter, createWebHashHistory } from 'vue-router'
const Login=()=>import("../views/Login.vue")
const MainLayout=()=>import("@/views/layout/MainLayout.vue")
const User=()=>import("@/views/sys/user/Index.vue")
const Employee=()=>import("@/views/employee/Index.vue")
import {getUser} from "@/utils/session.js"



//配置路由
const routes=[
  {
    path:"/",
    name:"login",
    component:Login
  },
  {
    path:"/main",
    name:"main",
    component:MainLayout,
    children:[
      {
        path:"/user",
        name:"user",
        component:User
      },
      {
        path:"/employee",
        name:"employee",
        component:Employee
      },
    ]
  }
  
];

/**
 * 创建路由实例 
 * history 配置访问模式 这里是hash模式  地址 /#/hash值  
 */
const router = createRouter({
  history: createWebHashHistory(),
  routes  //  等价于  routes:routes
})
//配置全局守卫
router.beforeEach((to, from, next) => {
  //说明用户访问的是登录页面 放行
  if (to.path == "/") {
    next();
    return;
  }
  //获取登录信息
  let user = getUser();
  if (user.id == "") {
    //没有登录 跳转到登录页面
    next("/");
    return;
  }
  //登录过 直接放行
  next();
});

export default router
