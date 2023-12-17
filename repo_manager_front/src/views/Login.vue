<!--vue模板-->
<template>
  <div class="root" @keyup.enter="login">
  <div class="login">
    <div class="title">ERP管理系统</div>
    <el-form ref="loginForm" label-width="90px" :model="user" :rules="rules">
      <el-form-item label="用户名" prop="username">
        <el-input class="ipt" placeholder="请输入用户名" v-model="user.username" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" class="ipt"  placeholder="请输入密码" v-model="user.password"/>
      </el-form-item>
    </el-form>

    <div style="text-align: center;">
     <el-button type="primary" @click="login" @keyup.enter="login">登录</el-button>
    </div>
  </div>
</div>
</template>

<!--vue 组件选项定义-->
<script setup>
import {ref, reactive, onBeforeMount } from "vue";
import { ElMessage } from 'element-plus'
import {useRouter} from 'vue-router'
import {userLogin} from '@/api'
import { setUser } from "../utils/session";
import useTabsStore from '@/stores/tabs.js'
import userTabsStore from "@/stores/tabs.js";
/***********响应式对象*********** */
const user=reactive({
     username:"",
     password:""
});
const tabsStore=userTabsStore();
//路由对象
const router=useRouter();
//要求与form表单中的ref值一样  
const loginForm=ref(null);
//验证规则 
const rules={
     username:[{ required: true, message: '请输入用户名', trigger: 'blur' }],
     password:[{ required: true, message: '请输入密码', trigger: 'blur' }]
};

const store=useTabsStore();


/***********方法********** */
 function login(){
     loginForm.value.validate(rs=>{
          //验证没有通过 
          if(!rs){
               /*ElMessage({
                    message:"请填写数据！",
                    type:"warning"

               });*/
               return;//直接返回 程序 不再执行  
          }

          //验证通过 
          userLogin(user)
          .then(res=>{
            console.log("登录",res);
              if(res.code==200){
                ElMessage({
                    message:"登录成功",
                    type:"success"
               });
               //将用户信息保存到本地
               setUser(res.data)
               //跳转
               goto("/main","通知")
              }
          })
     });
}

function goto(url,name)
{
  let id = url.replaceAll("/", "");
  tabsStore.addTab({ url, name, id });
  console.log("url",url);
  router.push(url);
}
/*************生命周期********************* */
onBeforeMount(()=>{
  store.clear();
})
</script>

<!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.root{
  height: 100%;
  background-image: url(../assets/bcakground.jpeg);
  overflow: hidden;
}
.login {
  width: 500px;
  height: 300px;
  background-color: rgba(246, 233, 233,0.7);
  border: #ddd solid 2px;
  margin: 200px auto;
  border-radius: 10px;
}
.title {
  text-align: center;
  font-size: 36px;
  height: 80px;
  line-height: 80px;
}
.ipt {
  width: 80%;
}
</style>
