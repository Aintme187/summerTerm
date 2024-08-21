package com.example.usercenterapi.controller;

import com.example.usercenterapi.service.LoginService;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
