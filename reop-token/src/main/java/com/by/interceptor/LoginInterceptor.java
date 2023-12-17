package com.by.interceptor;

import com.by.token.TokenFactory;
import com.by.token.ValidResult;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    TokenFactory tokenFactory;

    public LoginInterceptor(TokenFactory tokenFactory){
        this.tokenFactory=tokenFactory;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader("token");
        if (null == token) {
            handler(response, "无权访问");
            return false;
        }

        //有token 验证
        ValidResult result = tokenFactory.verifyToken(token);
        if (result.getCode() == 0) {
            handler(response, result.getMsg());
            return false;
        }

        //如果需要获取用户或权限等信息
        //result.getPayload().get("id");

        return true;
    }

    void handler(HttpServletResponse response, String msg) {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pw.write("{\"code\":0 , \"msg\":\"" + msg + "\"}");
    }

}
