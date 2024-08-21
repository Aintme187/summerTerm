package com.example.usercenterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class UserCenterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterServiceApplication.class, args);
        System.out.println("个人中心后端服务启动成功！");
    }

}