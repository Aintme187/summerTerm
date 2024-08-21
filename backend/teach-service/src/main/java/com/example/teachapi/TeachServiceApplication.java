package com.example.teachapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class TeachServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachServiceApplication.class, args);
        System.out.println("教学后端服务启动成功！");
    }

}