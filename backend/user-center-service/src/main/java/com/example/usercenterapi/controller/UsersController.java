package com.example.usercenterapi.controller;

import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.service.SysUserService;
import com.example.usercenterapi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 1.通过RequestHeader获取Authorization中的token
     * 2.利用token获取用户信息
     * @param token
     * @return
     */
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }


    @PutMapping("updateUser")
    public Result updateUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateUser(sysUser);
    }

    @GetMapping("getMyInfo")
    public Result getMyInfo(@RequestParam Long id) {
        return sysUserService.getInfoById(id);
    }


    /**
     * ???
     * @param id
     * @param url
     * @return
     */
    @PutMapping("updateAvatar")
    public Result updateAvatar(Long id,String url) {
        return sysUserService.updateAvatar(id,url);
    }
}
