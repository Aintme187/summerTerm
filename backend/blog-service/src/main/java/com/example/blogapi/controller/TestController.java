package com.example.blogapi.controller;

import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.utils.UserThreadLocal;
import com.example.blogapi.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @PostMapping
    public Result test(){

        return Result.success(null);
    }



}

