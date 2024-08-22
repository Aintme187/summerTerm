package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-center-service")
public interface UserClient {

    @PostMapping("/users")
    SysUser queryItemByIds(@RequestParam("id") Long id);
}