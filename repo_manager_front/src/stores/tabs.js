import { ref, onBeforeMount } from "vue"
import { defineStore } from "pinia";
import { LOCAL_TABS, LOCAL_CURRENT_TAB } from "./constant";
//定义存储对象 名称 官方建议：use+store名称+store
const useTabsSrore = defineStore("tabs", () => {
    //定义响应式对象 等价于state 当前记录的所有页面
    const tabs = ref([]);
    const currentTab = ref("")//当前激活的标签页
    function addTab(tab) {
        //检查当前tabs中是否存在tab对象
        //将tab对象加入标签列表
        tabs.value.push(tab);
        currentTab.value = "";
        //标志变量
        let flag = false;
        for (let i = 0, len = tabs.value.length; i < len; i++) {
            let tmp = tabs.value[i];
            //判断当前对象与新增对象路径是否一样
            if (tab.url == tmp.url) {
                flag = true;
                currentTab.value = tmp.id;
                localStorage.setItem(LOCAL_CURRENT_TAB, currentTab.value)
                break;
            }
        }
        if (!flag) {
            tabs.value.push(tab)
            currentTab.value = tab.value;
            localStorage.setItem(LOCAL_TABS, JSON.stringify(tabs.value))
            localStorage.setItem(LOCAL_CURRENT_TAB, currentTab.value)
        }
    }
    /****************生命周期********************* */
    onBeforeMount(() => {
        let tmpTabs = localStorage.getItem(LOCAL_TABS);
        if (!tmpTabs) {
            return;
        }
        tabs.value = JSON.parse(tmpTabs);
        currentTab.value = localStorage.getItem(LOCAL_CURRENT_TAB);
    })
    //删除标签页
    function remove(id) {
        for (let i = 0, len = tabs.value.length; i < len; i++) {
            if (tabs.value[i].id == id) {
                tabs.value.splice(i, 1);
                localStorage.setItem(LOCAL_TABS, JSON.stringify(tabs.value));
                break; //终止循环
            }
        }
        //调整激活的便签状态
        //判断标签是否还有数据
        let len = tabs.value.length;
        if (len == 0)
            return;
        currentTab.value = tabs.value[len - 1].id;
        localStorage.setItem(LOCAL_CURRENT_TAB, currentTab.value);
    }
    function clear() {
        localStorage.removeItem(LOCAL_TABS);
        localStorage.removeItem(LOCAL_CURRENT_TAB);
        tabs.value = [];
        currentTab.value = ""
    }
    //暴露接口
    return { tabs, addTab, currentTab, clear, remove };

});

export default useTabsSrore;