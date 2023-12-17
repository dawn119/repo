<!--vue模板-->
<template>
  <!--头部-->
  <Header></Header>
  <!--下半部分-->
  <div class="container">
    <!--左侧导航 -->
    <LeftNav></LeftNav>
    <!--工作区-->
    <WorkSpace>
      <router-view></router-view>
    </WorkSpace>
  </div>
</template>

<!--vue 组件选项定义-->
<script setup>
import Header from "./Header.vue";
import LeftNav from './LetfNav.vue'
import WorkSpace from './WorkSpace.vue'
import { ref } from "vue";
</script>

<!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.container {
  height: calc(100% - 80px);
  display: -webkit-flex;
  display: flex;

}
</style>
