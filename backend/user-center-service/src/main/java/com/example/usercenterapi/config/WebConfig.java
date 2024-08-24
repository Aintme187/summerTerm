package com.example.usercenterapi.config;

import com.example.usercenterapi.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        //本地测试 端口不一致 也算跨域
        //以下代码允许8080端口访问接口服务
        //registry.addMapping("/**").allowedOrigins("http://localhost:5173","http://47.94.206.220");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截需要登录后才能访问的接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish")
                .addPathPatterns("/messages/list")
                .addPathPatterns("/favorites/showFavorite")
                .addPathPatterns("/favorites/create")
                .addPathPatterns("/categorys/create")
                .addPathPatterns("/tags/create")
                .addPathPatterns("/articles/like/{id}")
                .addPathPatterns("/api")
                .addPathPatterns("/admin");
    }
}
