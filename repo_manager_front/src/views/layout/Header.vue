<!--vue模板-->
<template>
  <div class="header">
    <div style="font-size: 36px;">XXX管理系统</div>
    <!--用户信息及菜单-->
    <div  class="headRight">
      <img src="../../assets/a.jpg" class="headimg" />
      <el-dropdown @command="handlerCommand">
        <span class="el-dropdown-link" style="color: #fff">
          {{user.account || "无"}}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="pwd">修改密码</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
  <!--修改密码-->
  <ModifyPwd :visible="isshow" @close="isshow=false"></ModifyPwd>
</template>

<!--vue 组件选项定义-->
<script setup>
import { ref } from "vue";
import { ArrowDown } from "@element-plus/icons-vue";
import { getUser,logout } from "../../utils/session";
import ModifyPwd from "./ModifyPwd.vue"
import { ElMessageBox } from "element-plus";
import { useRouter } from "vue-router";
const router=useRouter();
const user=ref(getUser())
const isshow=ref(false);//是否显示密码

/*************方法**************** */
function handlerCommand(command)
{
  if(command=="pwd")
  {
    isshow.value=true;
    console.log(isshow.value);
  }else{
    ElMessageBox.confirm("确定要退出吗?", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        logout();
        router.push("/");
      })
      .catch(() => {});
  }
}
</script>

<!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.header {
  height: 80px;
  background-color: #545c64;
  color: #fff;
  display: -webkit-flex;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-right: 30px;
  padding-left: 10px;
}

.headRight{
  display: -webkit-flex;
  display: flex;
  align-items: center;
}

.headimg {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}
</style>
