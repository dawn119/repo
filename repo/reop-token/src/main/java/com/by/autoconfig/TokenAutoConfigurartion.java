package com.by.autoconfig;

import com.auth0.jwt.JWT;
import com.by.interceptor.LoginInterceptor;
import com.by.token.TokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自动配置类
 */
@AutoConfiguration// 说明当前类是自己配置类
@ConditionalOnClass({JWT.class, HandlerInterceptor.class, WebMvcConfigurer.class})
@EnableConfigurationProperties({TokenProperties.class})//用来绑定当前应用的属性配置
@ConditionalOnProperty(
        prefix = "token",//配置的前缀
        value = "enabled", //属性名  用来控制当前控制类是否生效
        matchIfMissing = true //只要引入当前启动器  默认生效
)
public class TokenAutoConfigurartion {

    TokenProperties properties;

    /**
     * 向容器注入token工具类
     *
     * @return
     */
    @Bean
    public TokenFactory tokenFactory(TokenProperties tokenProperties) {
        this.properties = tokenProperties;
        return new TokenFactory(tokenProperties);

    }

    /**
     * 登录拦截器 要求TokenFactory注入容器后 才会生效
     *
     * @param tokenFactory
     * @return
     */
    @Bean
    @ConditionalOnBean(value = {TokenFactory.class})
    public LoginInterceptor loginInterceptor(TokenFactory tokenFactory) {
        return new LoginInterceptor(tokenFactory);
    }


    /**
     * 注入拦截器的
     */
    @Configuration(proxyBeanMethods = false)
    class InterceptorRegister implements WebMvcConfigurer {

        @Autowired
        LoginInterceptor loginInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            if (null == loginInterceptor)
                return;
            registry.addInterceptor(loginInterceptor)
                    .addPathPatterns(properties.getPathPatterns())
                    .excludePathPatterns(properties.getExcludePathPatterns());

        }
    }


}
