package com.example.usercenterapi.controller;

import com.example.usercenterapi.vo.Result;
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

