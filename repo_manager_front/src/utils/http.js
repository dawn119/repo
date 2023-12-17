//网络请求模块
//引入请求模块
import axios from "axios";
import host from "./env.js";
import { ElMessage } from 'element-plus'
import {getToken} from './session.js'
//创建axios实例
const instance = axios.create({
  baseURL: host,
  timeout: 5000,
});



// 添加请求拦截器
instance.interceptors.request.use(function (config) {
  config.headers.token=getToken();
  console.log(config);  
  // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    //说明http请求成功
    if(response.status==200){
      let obj=response.data;
       //将接口返回的数据包装成promise对象  返回
       if(typeof(obj)=='string'){
        obj=JSON.parse(obj);
    }
    if (obj.code == 0) {
      //如果接口为0 提示消息
      ElMessage({
        message: obj.msg,
        type: "error",
      });
      }
       return Promise.resolve(obj);
    }
   // return null;
  }, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么

    let msg="";

    //判断服务器有没有响应 
    if(!error.response){
        if(error.code=="ERR_NETWORK"){
           msg="服务器没启动！";
        }else{
          msg=error.code;
        }
    }else{
      let res=error.response;
      if(res.status==404){
          msg="请求的路径不存在";
      }else{
        msg="请求错误";
      }
    }

    ElMessage({
        message:msg,
        type:"error"
    });
  });


/**
 * 导出一个对象  包含常用请求方法
 */
export default {
  /**
   *
   * @param {*} url 接口地址
   * @param {*} params  get请求参数 json对象
   */
  get(url, params) {
    return instance.get(url, { params }); //params:params
  },

  /**
   *
   * @param {*} url 接口地址
   * @param {*} data  请求的json格式 数据
   * @returns
   */
  post(url, data) {
    return instance.post(url, data);
  },

/**
   *
   * @param {*} url 接口地址
   * @param {*} data  请求的json格式 数据
   * @returns
   */
  put(url,data){
    return instance.put(url,data);
  },

  /**
   * 
   * @param {*} url 接口地址
   * @param {*} id  要删除的id  
   * @returns 
   */
  delete(url,id,datanum){
    return instance.delete(url,{params:{id,datanum}});
  }
};
