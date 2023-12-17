<!--vue模板-->
<template>
  <el-dialog :model-value="visible" width="35%"  :title="user.id ? '修改用户' : '添加用户'"
    :close-on-click-modal="false" @close="close">

    <el-form label-width="100px" :model="user" :rules="rules" ref="suForm">
      <el-form-item label="账号" prop="account">
        <el-input class="ipt" v-model="user.account" placeholder="请输入账号" @blur="checkuser"
          :disabled="user.id != undefined"></el-input>
      </el-form-item>

      <el-form-item label="有效日期">
        <el-date-picker v-model="user.effectiveDate" type="date" placeholder="请选择日期" value-format="YYYY-MM-DD" />
      </el-form-item>

      <el-form-item label="电子签名">
        <el-upload class="avatar-uploader" :action="uploadPath" :show-file-list="false" :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="员工">
        <el-input class="ipt" v-model="user.name" placeholder="请选择员工" disabled></el-input>
      </el-form-item>
      <el-form-item label="角色">
        <el-select v-model="user.roles">
             <el-option v-for="item in roles" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
          </el-form-item>
      <el-form-item label="说明">
        <el-input class="ipt" v-model="user.note" type="textarea" rows="5"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center;">
      <el-button type="primary" @click="save">保存</el-button>
      <el-button type="info" @click="close">取消</el-button>
    </div>
  </el-dialog>
</template>
 
 <!--vue 组件选项定义-->
<script setup>

import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { checkUser, saveUser, updateUser } from "@/api"
import host from "@/utils/env.js";


/*************响应式对象************ */
//定义属性 父传子 用于新增打开
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
});
//声明事件 子传父（依赖注入）用来关闭和更新 调用方法
const emit = defineEmits(["close", "refreshData"]);
const isExist = ref(false);
const suForm = ref(null);
const imageUrl = ref("")
const uploadPath = ref(host + "/api/upload");//上传路径
const roles=ref([]);
//用户
const user = ref({
  account: "",
  effectiveDate: "",
  signUrl: "",
  note: "",
  eid: "",
  name: "",
  roles: "",
})

//验证规则
const rules = {
  account: [{ required: true, message: "请输入账号", trigger: "blur" }],
};
/**********方法********** */
//查询角色
// async function queryRole(){
//    const res=await queryAllRole();
//    if(res.code==200){
//       roles.value=res.data;
//    }
// }
//初始化参数
function init(data) {
  if (data == undefined) {
    user.value = {
      account: "",
      effectiveDate: "",
      signUrl: "",
      note: "",
      eid: "",
      name: "",
      roles: "",
    };
    imageUrl.value = ""
  } else {
    //修改
    user.value = data;
    console.log(data);
    //判断图片是否存在
    if (data.signUrl) {
      imageUrl.value = host + "/" + data.signUrl;
    } else {
      imageUrl.value = ""
    }
  }
  queryRole();
}
//关闭
function close() {
  emit("close")
}
//检查账号是否唯一
async function checkuser() {
  const res = await checkUser(user.value.account)
  console.log(res);
  if (res.code == 200) {
    if (res.data == 1) {
      ElMessage({
        message: "该账号已存在！",
        type: "warning"
      });
      isExist.value = true;
    } else {
      isExist.value = false;
    }
  }
}
//保存
function save() {
  suForm.value.validate((valid) => {
    if (!valid) {
      return;//没有验证通过 直接返回
    }
  })

  //保存用户
  if (isExist.value) {
    ElMessage({
      message: "该账户已存在",
      type: "warning",
    })
    return;
  }
  let propmise;
  if (user.value.id) {//如果有ID执行修改
    propmise = updateUser(user.value)
  } else {
    //执行保存
    propmise = saveUser(user.value)
  }
  propmise.then((res) => {
    if (res.code == 200) {
      ElMessage({
        message: res.msg,
        type: "success"
      });
      close();//关闭对话框 
      emit("refreshData");//通知父组件刷新数据 

    }
  })
}

//上传图片
//上传前
function beforeAvatarUpload(rawFile) {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('请上传jpeg格式')
    return false
  } else if (rawFile.size / 1024 / 1024 > 5) {
    ElMessage.error('图片上传不能超过2MB!')
    return false
  }
  return true
}
//上传成功
function handleAvatarSuccess(response, uploadFile) {
  if (response.code == 200) {
    imageUrl.value = URL.createObjectURL(uploadFile.raw);
    user.value.signUrl = response.data;
  }
}


/********暴露接口************ */
defineExpose({ init })



</script>
 
 <!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.ipt {
  width: 90%;
}

/* 上传图片 */

.avatar {
  width: 78px;
  height: 78px;
}

:deep(.avatar-uploader .el-upload) {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

:deep(.avatar-uploader .el-upload:hover) {
  border-color: var(--el-color-primary);
}

:deep(.el-icon.avatar-uploader-icon) {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
}
</style>