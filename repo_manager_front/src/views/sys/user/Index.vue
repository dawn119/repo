<!--vue模板-->
<template>
  <!--工具条-->
  <div class="toolbar">
    <el-row>
      <el-col :span="2" style="display: flex; justify-content: end;">账号：</el-col>
      <el-col :span="4">
        <el-input placeholder="请输入账号" clearable v-model="filters.account"></el-input>
      </el-col>
      <el-col :span="2" style="display: flex; justify-content: end;">姓名：</el-col>
      <el-col :span="4">
        <el-input placeholder="请输入员工姓名" clearable v-model="filters.name"></el-input>
      </el-col>
      <el-col :span="2" style="display: flex; justify-content: end;">是否锁定：</el-col>
      <el-col :span="4">
        <el-select v-model="filters.isLock" class="m-2" placeholder="是否锁定">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="initData">查询</el-button>
        <el-button type="primary" @click="saveOrUpdate()">新增</el-button>
      </el-col>
    </el-row>

  </div>
  <!--表格数据-->
  <el-table :data="tableData" style="width: 100%" border stripe>
    <el-table-column type="expand" width="50">
      <template #default="{ row }">
        <el-row>
          <el-col :span="4" class="label">账号:</el-col>
          <el-col :span="8">{{ row.account }}</el-col>
          <el-col :span="4" class="label">员工名称</el-col>
          <el-col :span="8">{{ row.name }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="label">是否锁定:</el-col>
          <el-col :span="8">
            <el-tag v-if="row.isLock == 0">否</el-tag>
            <el-tag type="danger" v-else>是</el-tag>
          </el-col>
          <el-col :span="4" class="label">生效日期</el-col>
          <el-col :span="8">{{ row.effectiveDate }}</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="label">创建日期:</el-col>
          <el-col :span="8">
            {{ row.createTime }}
          </el-col>
          <el-col :span="4" class="label">更新日期:</el-col>
          <el-col :span="8">{{ row.updateTime }}</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="label">角色:</el-col>
          <el-col :span="8">
            {{ row.roles }}
          </el-col>
          <el-col :span="4" class="label">电子签名:</el-col>
          <el-col :span="8">
            <img :src="host + '/' + row.signUrl" v-if="row.signUrl" style="width: 100px; height: 100px;" />
            <span v-else>暂无</span>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="label">备注:</el-col>
          <el-col :span="20">
            {{ row.note }}
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="label">最近登录日期:</el-col>
          <el-col :span="20">
            {{ row.loginTime }}
          </el-col>
        </el-row>
      </template>
    </el-table-column>

    <el-table-column type="index" label="行号" width="80" header-align="center" align="center" />
    <el-table-column prop="account" label="账号" width="180" header-align="center" align="center" />
    <el-table-column prop="isLock" label="是否锁定" width="180" header-align="center" align="center">
      <template v-slot="{ row }">
        <el-tag v-if="row.isLock == 0">否</el-tag>
        <el-tag type="danger" v-else>是</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="effectiveDate" label="生效日期" width="180" header-align="center" align="center" />
    <el-table-column prop="name" label="员工姓名" width="180" header-align="center" align="center" />
    <el-table-column prop="roleName" label="角色" width="180" header-align="center" align="center" />
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
import { queryUser, deleteUser } from "@/api"
import { Delete, Edit } from "@element-plus/icons-vue";
import SaveOrUpdate from './SaveOrUpdate.vue'
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
  account: "",
  name: "",
  isLock: "",
})
const options = [
  {
    label: "是",
    value: 1
  },
  {
    label: "否",
    value: 0
  }
]


/***************生命周期***************** */
onBeforeMount(() => {
  initData()
});
/***********方法*************** */
//查询数据
async function initData() {
  let params = {
    page: page.value,
    pageSize: pageSize.value,
    account: filters.account,
    name: filters.name,
  }
  if (filters.isLock != "") {
    params.isLock = filters.isLock;
  }
  //请求调用获取结果
  const res = await queryUser(params)
  if (res.code == 200) {
    //更新表数据
    tableData.value = res.data.data;
    page.value = res.data.page;
    pageSize.value = res.data.pageSize;
    total.value = res.data.total;
  }
}
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
      deleteUser(id).then((res) => {
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