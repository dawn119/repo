<!--vue模板-->
<template>
  <!--工具条-->
  <div class="toolbar">
    <el-row>
      <el-col :span="4">
        <el-input placeholder="姓名" clearable v-model="filters.empName"></el-input>
      </el-col>
      <el-col :span="4">
        <el-select v-model="filters.deptName" class="m-2" placeholder="部门">
          <el-option v-for="item in dept" :key="item.id" :label="item.deptName" :value="item.id" />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-select v-model="filters.empDegreeName" class="m-2" placeholder="学历">
          <el-option v-for="item in degree" :key="item.id" :label="item.degreeName" :value="item.id" />
        </el-select>
      </el-col>

      <el-col :span="12">
        <el-button type="primary" @click="initData">查询</el-button>
        <el-button type="primary" @click="saveOrUpdate()">新增</el-button>
        <el-button type="primary" @click="clear()">清空</el-button>
        <el-button type="primary" @click="exportEmployees()">导出</el-button>
      </el-col>
    </el-row>

  </div>
  <!--表格数据-->
  <el-table :data="tableData" style="width: 100%" border stripe>

    <el-table-column type="index" label="序号" width="80" header-align="center" align="center" />
    <el-table-column prop="empName" label="职工名称" width="180" header-align="center" align="center" />
    <el-table-column prop="sex" label="性别" width="180" header-align="center" align="center">
      <template v-slot="{ row }">
        <el-tag v-if="row.sex == 0">男</el-tag>
        <el-tag type="danger" v-else>女</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="age" label="年龄" width="180" header-align="center" align="center" />

    <el-table-column prop="deptName" label="部门名称" width="180" header-align="center" align="center">
      <template v-slot="{ row }">
        <el-tag v-if="row.deptName == 1">业务部</el-tag>
        <el-tag v-if="row.deptName == 2">后勤部</el-tag>
        <el-tag v-if="row.deptName == 3">人事部</el-tag>
      </template>
    </el-table-column>

    <el-table-column prop="empDegreeName" label="学历" width="180" header-align="center" align="center">
      <template v-slot="{ row }">
        <el-tag v-if="row.empDegreeName == 1">专科</el-tag>
        <el-tag v-if="row.empDegreeName == 2">本科</el-tag>
        <el-tag v-if="row.empDegreeName == 3">研究生</el-tag>
      </template>
    </el-table-column>
  

    <el-table-column label="操作" header-align="center" align="center">
      <template v-slot="{ row }">
        <el-button type="primary" :icon="Edit" circle title="编辑" @click="saveOrUpdate(row)" />
        <el-button type="danger" :icon="Delete" circle title="删除" @click="del(row.id)" />
      </template>
    </el-table-column>
  </el-table>
  <!--分页信息-->
  <!--
    current-change:改变时触发 改变页数
    size-change:改变时触发   
  -->
  <el-pagination @current-change="chgpg" @size-change="chgsize" :page-sizes="[1, 10, 20, 30, 40]" background
    layout="total, sizes, prev, pager, next" :total="total" v-model:current-page="page" v-model:page-size="pageSize"
    class="page" />
  <!--保存或修改-->
  <SaveOrUpdate ref="saveupdate" :visible="isshow" @close="isshow = false" @refreshData="initData">
  </SaveOrUpdate>
</template>

<!--vue 组件选项定义-->
<script setup>
import { ref, onBeforeMount, reactive, nextTick } from 'vue'
import { queryEmployee, deleteEmployee,queryDept,queryDegree,exportEmployee } from "@/api"
import { Delete, Edit } from "@element-plus/icons-vue";
import SaveOrUpdate from "./SaveOrUpdate.vue"
import host from "@/utils/env.js";
import { ElMessage, ElMessageBox } from "element-plus";
/***********响应对象*************** */
const tableData = ref([])//表格数据
const page = ref(1)
const pageSize = ref(10)
const total = ref(0);
const isshow = ref(false)
const saveupdate = ref(null);
const filters = reactive({
  empName: "",
  deptName: "",
  empDegreeName: "",
})

const dept = ref([]);
const degree = ref([]);
let exportEmployeeData = ref([]);


/***************生命周期***************** */
onBeforeMount(() => {
  initData();
  queryDepts();
  queryDegrees();
});
/***********方法*************** */
//查询数据
async function initData() {
  let params = {
    page: page.value,
    pageSize: pageSize.value,
    empName: filters.empName,
  }
  if (filters.deptName != "") {
    params.deptName = filters.deptName;
  }
  if (filters.empDegreeName != "") {
    params.empDegreeName = filters.empDegreeName;
  }
  //请求调用获取结果
  const res = await queryEmployee(params)
  if (res.code == 200) {
    //debugger;
    //更新表数据
    tableData.value = res.data.data;
    exportEmployeeData = res.data.data.map(({ id, deId, did, ...rest }) => rest);
    console.log("export",exportEmployeeData);
    page.value = res.data.page;
    pageSize.value = res.data.pageSize;
    total.value = res.data.total;
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

//页数跳转
function chgpg(num) {
  //更改当前页
  page.value = num;
  //重新查询
  initData();
}
//每页记录数
function chgsize(num) {
  pageSize.value = num;
  page.value = 1;
  initData();
}
//删除
function del(id) {
  ElMessageBox.confirm("您确定要删除吗?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      //执行删除
      deleteEmployee(id).then((res) => {
        if (res.code == 200) {
          ElMessage.success("删除成功");
          initData();
        }
      });
    })
    .catch(() => { });
}


//保存或修改
function saveOrUpdate(row) {
  //显示修改矿
  isshow.value = true;
  //调用子主件 并将参数传递
  saveupdate.value.init(row);
 
}

function clear(){
  filters.deptName="";
  filters.empDegreeName="";
  filters.empName="";
}

async function exportEmployees(){
  //debugger;
  const params = exportEmployeeData;
  console.log("params",params);
  const res = await exportEmployee(exportEmployeeData);
  if(res.code=200){
    ElMessage.success(res.msg);
  }
}


</script>

<!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.label {
  text-align: right;
  color: #999;
  font-weight: bold;
  padding-right: 15px;
}

:deep(.el-row) {
  line-height: 40px;
}

:deep(.el-input ){
  width: 214px;
}

.page {
  display: -webkit-flex;
  display: flex;
  justify-content: flex-start;
  padding-left: 40px;
  margin-top: 30px;
}

.toolbar {
  height: 50px;
  line-height: 50px;
  padding-left: 20px;
}
</style>