package com.example.usercenterapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!user
 *
 */
@SpringBootApplication
@MapperScan("com.example.usercenterapi.dao.mapper")
public class UserCenterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterServiceApplication.class, args);
        System.out.println("个人中心后端服务启动成功！");
    }
}