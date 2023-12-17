package com.by.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class ApiConfig {
    @Bean
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                //.enable(true)//为true可以访问 false不能访问
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.by.controller"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("xxx")
                .description("xxx管理系统")
                .contact(new Contact("秦志颖", "http://localhost", "17737812660@163.com"))
                .version("v1.0")
                .build();
    }
}
