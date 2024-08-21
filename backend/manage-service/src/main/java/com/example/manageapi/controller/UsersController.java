package com.example.manageapi.controller;

import com.example.manageapi.dao.pojo.SysUser;
import com.example.manageapi.service.SysUserService;
import com.example.manageapi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
@CrossOrigin
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
    @CrossOrigin
    public Result updateUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateUser(sysUser);
    }

    @GetMapping("getMyInfo")
    @CrossOrigin
    public Result getMyInfo(Long id) {
        return sysUserService.getInfoById(id);
    }

    @PutMapping("updateAvatar")
    @CrossOrigin
    public Result updateAvatar(Long id,String url) {
        return sysUserService.updateAvatar(id,url);
    }
}
