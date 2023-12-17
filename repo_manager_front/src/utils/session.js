const SESSION_NAME = "erp_user"; //会话存储的key
//将用户信息存储到本地
export function setUser(user) {
  sessionStorage.setItem(SESSION_NAME, JSON.stringify(user));
}

//读取 会话信息
export function getUser() {
  let user = sessionStorage.getItem(SESSION_NAME);
  if (user) {
    //转换后返回
    return JSON.parse(user);
  } else {
    return {
      id: "",
      account: "",
      name: "",
      roles: "",
      token: "",
    };
  }
}
//获取token
export function getToken() {
  let tmp = getUser();
  return tmp.token;
}
//退出
export function logout(){
  sessionStorage.removeItem(SESSION_NAME);
}