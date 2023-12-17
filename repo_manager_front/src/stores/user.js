import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

//定义存储对象 名称 官方建议：  use+store名称+Store
 const useUserStore = defineStore('user', () => {
  //定义响应式对象  等价于state
  const count = ref(0)

  //计算属性 等价于  getter
  const doubleCount = computed(() => count.value * 2)

  //函数 相当于 action  
  function increment() {
    count.value++
  }

  //暴露接口 
  return { count, doubleCount, increment }
})

//暴露接口
export default useUserStore
