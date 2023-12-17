import { ref } from 'vue'
import { defineStore } from 'pinia'

//定义存储对象 名称 官方建议：  use+store名称+Store
 const useLayoutStore = defineStore('layout', () => {
  //定义响应式对象  等价于state 左侧导航宽度
  const navWidth = ref(200)

  //更改导航宽度 折叠菜单
  function close(){
    navWidth.value=64;
  }

  function open(){
    navWidth.value=200;
  }

  //暴露接口 
  return { navWidth,close,open }
})

//暴露接口
export default useLayoutStore
