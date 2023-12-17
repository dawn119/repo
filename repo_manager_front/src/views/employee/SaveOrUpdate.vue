<!--vue模板-->
<template>
  <el-dialog :model-value="visible" width="35%"  :title="employee.id ? '修改职员信息' : '添加职员信息'"
    :close-on-click-modal="false" @close="close">
    <el-form label-width="100px" :model="employee"  ref="suForm">
      <el-form-item label="员工姓名" prop="empName">
        <el-input class="ipt" v-model="employee.empName" placeholder="请输入姓名" ></el-input>
      </el-form-item>

      <el-form-item label="年龄" prop="age">
        <el-input class="ipt" v-model="employee.age" placeholder="请输入年龄" 
          ></el-input>
      </el-form-item>

      <el-form-item label="部门">
        <el-select v-model="employee.deptName" class="m-2" placeholder="请选择部门">
          <el-option v-for="item in dept" :key="item.id" :label="item.deptName" :value="item.id" />
        </el-select>
      </el-form-item>

      <el-form-item label="学历">
        <el-select v-model="employee.empDegreeName" class="m-2" placeholder="请选择学历">
          <el-option v-for="item in degree" :key="item.id" :label="item.degreeName" :value="item.id" />
        </el-select>
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

import { ref, reactive, onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import {  saveEmployee, updateEmployee,queryDept,queryDegree } from "@/api"
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


const dept = ref([]);
const degree = ref([]);

//用户
const employee = ref({
  empName: "",
  sex: "",
  age: "",
  deptName: "",
  dId:"",
  deId:"",
  empDegreeName: "",
})

/***********生命周期********** */
onBeforeMount(() => {
  queryDepts();
  queryDegrees();
});

/**********方法********** */
//初始化参数
function init(data) {
  if (data == undefined) {
    employee.value = {
      empName: "",
      sex: "",
      age: "",
      deptName: "",
      empDegreeName: "",
    };
  } else {
    //修改
    employee.value = data;
    employee.value.deptName = data.did;
    employee.value.empDegreeName = data.deId;
    console.log(data);
  }
  
  
}

async function queryDepts(){
  //debugger;
  const params = {}
  const res1 = await queryDept(params);
  console.log(res1)
  if(res1.code==200){
    dept.value=res1.data.data;
  }
  
};

async function queryDegrees(){
  //debugger;
  const params = {}
  const res1 = await queryDegree(params);
  console.log(res1)
  if(res1.code==200){
    degree.value=res1.data.data;
  }
  
};


//关闭
function close() {
  emit("close")
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
  if (employee.value.id) {//如果有ID执行修改
    propmise = updateEmployee(employee.value)
  } else {
    //执行保存
    propmise = saveEmployee(employee.value)
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




/********暴露接口************ */
defineExpose({ init })



</script>
 
 <!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.ipt {
  width: 90%;
}


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