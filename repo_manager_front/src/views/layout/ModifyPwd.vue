<!--vue模板-->
<template>
    <el-dialog :model-value="visible" width="35%" title="修改密码" :close-on-click-modal="false" @close="close">

        <el-form label-width="100px" :model="user" :rules="rules" ref="suForm">
            <el-form-item label="原始密码" prop="oldpwd">
                <el-input class="ipt" v-model="user.oldpwd" placeholder="请输入原始密码" type="password"></el-input>
            </el-form-item>

            <el-form-item label="新密码" prop="newpwd">
                <el-input class="ipt" v-model="user.newpwd" placeholder="请输入新密码" type="password"></el-input>
            </el-form-item>

            <el-form-item label="确定密码" prop="confirmpwd">
                <el-input class="ipt" v-model="user.confirmpwd" placeholder="请输入确定密码" type="password"></el-input>
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

import { ref} from 'vue'
import { ElMessage } from 'element-plus'
import {getUser,logout} from '@/utils/session.js'
import { modifyPwd } from "@/api"
import { useRouter } from 'vue-router';


/*************响应式对象************ */
//路由对象
const router = useRouter();
const suForm = ref(null);

//定义属性 父传子 用于新增打开
const props = defineProps({
    visible: {
        type: Boolean,
        default: false
    }
});
//声明事件 子传父（依赖注入）用来关闭和更新 调用方法
const emit = defineEmits(["close"]);


//用户
const user = ref({
    id: "",
    oldpwd: "",
    newpwd: "",
    confirmpwd: "",
})

//验证规则
const rules = {
    oldpwd: [{ required: true, message: "请输入原始密码", trigger: "blur" }],
    newpwd: [{ required: true, message: "请输入新密码", trigger: "blur" }],
    confirmpwd: [{ required: true, message: "请输入确定密码", trigger: "blur" }],
};
/**********方法********** */

//关闭
function close() {
    emit("close")
}

//保存
async function save() {
    suForm.value.validate((valid) => {
        if (!valid) {
            return;//没有验证通过 直接返回
        }
    })

    if (user.value.newpwd != user.value.confirmpwd) {
        ElMessage.warning("密码不一致，请重新输入");
        return;
    }
    user.value.id = getUser().id;
    const res = await modifyPwd(user.value);
    if (res.code == 200) {
        ElMessage.success("修改成功");
        logout();
        router.push("/");
    }


}







</script>
   
   <!--vue样式 scoped当前样式只适用于当前组件-->
<style scoped>
.ipt {
    width: 90%;
}
</style>