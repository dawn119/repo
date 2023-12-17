import http from '../utils/http'
//所有的接口
const Apis={
    login:"/api/sys/user/login" , //登录 
    queryUser:"/api/sys/user/query",//查询用户列表 
    checkUser:"/api/sys/user/check",//检查账号是否存在
    saveUser:"/api/sys/user",//保存用户
    deleteUser:"/api/sys/user",//删除用户
    updateUser:"/api/sys/user",//修改用户
    modifyPwd:"/api/sys/user/pwd",//修改密码
    refreshToken:"/api/sys/user/token",//更新token
    queryEmployee:"/api/emp/employee/query",//查询员工
    saveEmployee:"/api/emp/employee",//保存员工
    updateEmployee:"/api/emp/employee",//修改员工
    deleteEmployee:"/api/emp/employee",//删除员工
    queryDept:"/api/dept/query", //查询部门
    queryDegree:"/api/degree/query", //查询学历
    exportEmployee:"/api/emp/excel", //导出excel
};



//更新token 
export function refreshToken(data){
  return http.post(Apis.refreshToken,data);
}
//导出excel
export function exportEmployee(data)
{
    return http.post(Apis.exportEmployee,data)
}
 //查询学历
 export function queryDegree(data)
 {
     return http.post(Apis.queryDegree,data)
 }
 //查询部门
 export function queryDept(data)
 {
     return http.post(Apis.queryDept,data)
 }

//修改员工
export function updateEmployee(data)
{
    return http.put(Apis.updateEmployee,data)
}
//删除员工
export function deleteEmployee(id){
    return http.delete(Apis.deleteEmployee,id)
}

//保存员工
export function saveEmployee(data) {
    return http.post(Apis.saveEmployee, data);
  }
//查询员工
  export function queryEmployee(data)
{
    return http.post(Apis.queryEmployee,data)
}
//修改密码
export function modifyPwd(data){
    return http.put(Apis.modifyPwd,data)
}
//修改用户
export function updateUser(data)
{
    return http.put(Apis.updateUser,data)
}
//删除用户
export function deleteUser(id){
    return http.delete(Apis.deleteUser,id)
}

//保存用户
export function saveUser(data) {
    return http.post(Apis.saveUser, data);
  }
// 检查用户账号唯一
export function checkUser(account)
{
    return http.get(Apis.checkUser,{"account":account});
}
/**
 * 查询用户
 * @param {*} data 
 * @returns 
 */

export function queryUser(data)
{
    return http.post(Apis.queryUser,data)
}

/**
 * 登录
 */
export function userLogin(data){
    return http.post(Apis.login,data);
}