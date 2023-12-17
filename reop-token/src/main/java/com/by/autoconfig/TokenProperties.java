package com.by.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * token的属性绑定类
 */
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    final long DEFAULT_VALID_TIME = 30 * 60 * 1000; //默认30分钟
    final String DEFAULT_SECRET = "lkajdfolq2j3jklafsd"; //默认密钥

    final String[] DEFAULT_PATH_PATTERN = {"/**"};  //默然拦截所有的请求

    final String[] DEFAULT_EXCLUDE_PATH_PATTERN = {"/login"};

    String secret;

    long validTime;

    String[] pathPatterns;  //拦截器拦截的路径

    String[] excludePathPatterns; //不拦截的路径


    public String getSecret() {
        if (null == secret) {
            return DEFAULT_SECRET;
        }
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getValidTime() {
        if (validTime == 0) {
            return DEFAULT_VALID_TIME;
        }
        return validTime;
    }

    public void setValidTime(long validTime) {
        this.validTime = validTime;
    }

    public String[] getPathPatterns() {
        if (null == pathPatterns) {
            pathPatterns = DEFAULT_PATH_PATTERN;
        }
        return pathPatterns;
    }

    public void setPathPatterns(String[] pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public String[] getExcludePathPatterns() {
        if (null == excludePathPatterns) {
            excludePathPatterns = DEFAULT_EXCLUDE_PATH_PATTERN;
        }
        return excludePathPatterns;
    }

    public void setExcludePathPatterns(String[] excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }
}
