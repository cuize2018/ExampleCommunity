package com.xk.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc 删除该注解，否则无法加载css文件
//使用该注解时应覆写addResource方法，但是没有写，所以无法加载样式；
//不使用该注解时使用默认的资源路径
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }
}