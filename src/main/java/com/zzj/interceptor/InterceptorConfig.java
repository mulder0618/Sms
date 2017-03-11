package com.zzj.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by mulder on 2017/2/12.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInterceptor()).addPathPatterns("/*");
    }

}
