package com.example.manageapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class ManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageServiceApplication.class, args);
        System.out.println("管理后端服务启动成功！");
    }

}