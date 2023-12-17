<template>
    <div class="wrapper">
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <div class="title">
                        <span>公告</span>
                        <!-- <el-button class="button" text @click="clearAll()">一键已读</el-button> -->
                    </div>
                </div>
            </template>
            <ul>
                <template v-for="item in announcements" :key="item.id">
                    <li @click="showDetail(item)">
                        <div class="context">
                            <svg t="1689247277005" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="4518" width="40" height="40">
                                <path d="M512 624a112 112 0 1 0 0-224 112 112 0 0 0 0 224z" p-id="4519"
                                    :fill="item.status== 0 ? '#f78686' : '#6df5a4'">
                                </path>
                            </svg>
                            <div class="contextwrapper">
                                <div class="left">{{ item.title }}</div>
                                <div class="right">{{ item.updateTime }}</div>
                            </div>
                        </div>
                    </li>
                </template>
            </ul>
        </el-card>
        <div class="detail">
            <el-empty v-if="!detail" description="description" />
            <div v-else>
                <div class="contextTitle">
                    <h3>{{ detail.title }}</h3>
                </div>
                <div class="contextDate">
                    {{ detail.updateTime }}
                </div>
                <div class="constextDetail">
                    {{ detail.content }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onBeforeMount } from 'vue'
import { noticequerbyuser, noticenewread } from "@/Api"
import { getUser } from '../../../utils/session';


const announcements = ref()
const detail = ref()
const userId = ref({})

// async function clearAll(){}
async function init() {
    userId.value = getUser().id;
    const res = await noticequerbyuser(userId.value)
    console.log("aaaaaaaaaaaaaaaaaaaaaaaa", res)
    if (res.code == 200) {
        announcements.value = res.data
    }
}

async function showDetail(item) {
    const res = await noticenewread(item.id,getUser().id)
    if (res.code == 200) {
        detail.value = item
        init()
    }
}

onBeforeMount(() => { init() })

</script>

<style scoped>
.contextDate {
    color: #999;
    text-align: center;
}

.constextDetail {
    padding: 20px;
    height: 100px;
}

.contextTitle {
    font-size: 20px;
}

h3 {
    text-align: center;
}

.detail {
    width: 50%;
    height: 100%;
}

.contextwrapper {
    width: 100%;
    display: flex;
    justify-content: space-between;
}

.right {
    padding-right: 15px;
}

svg {
    flex-grow: 0;
}

.context {
    display: flex;
    justify-content: space-between;
    height: 40px;
    line-height: 40px;
}

li {
    font-size: 14px;
    border-radius: 5px;
}

ul {
    list-style: none;
    padding-left: 0px;
}

ul li::marker {
    color: red;
}

li:hover {
    background-color: #f5f7fa;
    cursor: pointer;
}

.button {
    color: #f78686;
}

.box-card {
    width: 50%;
    height: 100%;
}

.title {
    display: flex;
    justify-content: space-between;
}

.wrapper {
    display: flex;
    widows: 100%;
    height: 100%;
}
</style>