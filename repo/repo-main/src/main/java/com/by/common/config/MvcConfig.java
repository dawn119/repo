package com.by.common.config;

import com.by.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${app.prefix}")
    String prefix;

    /**
     * 设置接口统一前缀
     *
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //如果父类是BaseController  增加前缀
        configurer.addPathPrefix(prefix, clazz -> clazz.getSuperclass() == BaseController.class);
    }
    /**
     * 跨域处理
     * @param registry
     */
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/**")
                 .allowedOrigins("*")//允许的域名
                 .allowedMethods("get","post","put","delete","options")
                 .allowedHeaders("*")
                 .allowCredentials(false)
                 .maxAge(3600);
    }*/


    /**
     * 跨越配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 设置允许跨域请求的域名
        config.addAllowedOrigin("*");
        // 是否允许证书 不再默认开启
        // config.setAllowCredentials(true);
        // 设置允许的方法
        config.addAllowedMethod("*");
        // 允许任何头
        config.addAllowedHeader("*");
        config.addExposedHeader("token");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    /**
     * 静态资源处理
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


}
