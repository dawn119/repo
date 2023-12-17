<!--vue模板-->
<template>
  <div class="nav" :style="{ width: store.navWidth + 'px' }">
    <!--折叠展开-->
      <el-icon class="zdk">
        <Fold class="zd" @click="zd" v-if="store.navWidth == 200" />
        <Expand class="zd" @click="zk" v-else />
      </el-icon>

    <!--菜单-->
    <el-menu
      :collapse-transition="false"
      style="clear: both;"
      active-text-color="#ffd04b"
      background-color="#545c64"
      class="el-menu-vertical-demo"
      default-active="2"
      text-color="#fff"
      :collapse="store.navWidth != 200"
    >
    
      
    <el-sub-menu index="2">
        <template #title>
          <el-icon><Setting /></el-icon>
          <span>职工管理</span>
        </template>
        <el-menu-item index="2-1">
          <div @click="goto('','')">职工统计</div>
         
        </el-menu-item>
        <el-menu-item index="2-2">
          <div @click="goto('/employee','职工列表')">职工列表</div>
        
        </el-menu-item>
      </el-sub-menu>
      <!-- 系统管理 -->
      <el-sub-menu index="1">
        <template #title>
          <el-icon><Setting /></el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="1-1">
          <div @click="goto('/user','用户管理')">用户管理</div>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<!--vue 组件选项定义-->
<script setup>
import { Fold, Expand } from "@element-plus/icons-vue";
import useLayoutStore from "@/stores/layout.js";
import userTabsStore from "@/stores/tabs.js";
import { Setting } from "@element-plus/icons-vue";
import {useRouter} from "vue-router"

/**************状态对象*********** */
//存储对象
const store = useLayoutStore();
//标签对象
const tabsStore=userTabsStore();
const router=useRouter();

/***********方法*********** */
function goto(url,name)
{
  let id = url.replaceAll("/", "");
  tabsStore.addTab({ url, name, id });
  router.push(url);
}
//折叠
function zd() {
  store.close();
}
function zk() {
  store.open();
}
</script>

<!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.nav {
  height: 100%;
  background-color: #545c64;
}
.zd {
  color: #ddd;
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.zdk{
   width: 20px;
  height: 20px;
  float: right;
}

a{
  color: #fff;
  text-decoration: none;
}
</style>
