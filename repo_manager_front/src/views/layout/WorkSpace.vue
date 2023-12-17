<!--vue模板-->
<template>
  <div class="work">
    <el-tabs
  v-model="tabStore.currentTab"
      class="demo-tabs"
      @tab-change="handleClick"
      type="card"
      closable
      @tab-remove="rm"
    >
      <template v-for="(item, index) in tabStore.tabs" :key="index">
        <el-tab-pane :label="item.name" :name="item.id"></el-tab-pane>
      </template>

      <!-- <router-view></router-view> -->
    </el-tabs>
    <router-view></router-view>
  </div>
  
    <!-- <router-view v-slot="{ Component }">
      <keep-alive>
        <component :is="Component" />
      </keep-alive>
    </router-view> -->
</template>

<!--vue 组件选项定义-->
<script setup>
import { ref } from "vue";
import useLayoutStore from "../../stores/layout";
import userTabsStore from "@/stores/tabs.js";
import { useRouter } from "vue-router";
const store = useLayoutStore();
const tabStore = userTabsStore();
const router=useRouter();
/**********方法************* */
function handleClick(id) {
  let tabs = tabStore.tabs;
  let target; //要查找的目录对象
  for (let i = 0, len = tabs.length; i < len; i++) {
    let tmp = tabs[i];
    if (tmp.id == id) {
      target = tmp;
      break;
    }
  }


  if (target) {
    //更新当前激活的标识
    store.currentTab = target.id;
    //跳转
    router.push(target.url);
  }
}
function rm(tmp) {
  console.log("tmp>>>>", tmp);
  tabStore.remove(tmp);
}
</script>

<!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.work {
  height: calc(100%-80px);
  /* width: calc(100%-20px); */
  flex: 1;
  background-color: #fff;
  border: #f1f1f1 solid 10px;
  overflow-y: scroll;
  overflow-x: scroll;
}
</style>
