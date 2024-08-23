package com.example.teachapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@EnableFeignClients
@SpringBootApplication
@MapperScan
public class TeachServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachServiceApplication.class, args);
        System.out.println("教学后端服务启动成功！");
    }

}