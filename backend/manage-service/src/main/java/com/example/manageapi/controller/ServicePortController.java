package com.example.manageapi.controller;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicePortController {
    @Autowired
    private Environment env;

    @GetMapping("/admin/service-port")
    public String getServicePort() {
        // 获取端口号，Spring Boot 2.3及以上版本使用以下方式获取
        String port = env.getProperty("server.port");
        // 如果想获取随机端口号（如果设置了端口为0），可以使用以下方式
        // int port = env.getProperty("server.port", int.class, 0);
        return "The service is running on port: " + port;
    }
}
