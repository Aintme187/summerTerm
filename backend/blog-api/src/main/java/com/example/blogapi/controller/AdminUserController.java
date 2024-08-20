package com.example.blogapi.controller;

import com.example.blogapi.common.aop.LogAnnotation;
import com.example.blogapi.service.AdminSysUserService;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.SysUserInfoVo;
import com.example.blogapi.vo.params.AdminPageParam;
import com.example.blogapi.vo.params.BatchUpdateSysUsersParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/user")
public class AdminUserController {
    @Autowired
    private AdminSysUserService adminSysUserService;

    @PostMapping("list")
    @LogAnnotation(module = "管理用户", operator = "获取用户列表")
//    bug here
//    @Cache(expire = 5 * 60 * 1000, name = "sysUsers")
    public Result sysUsers(@RequestBody AdminPageParam adminPageParam) {
        return adminSysUserService.listSysUserPage(adminPageParam);
    }

    @GetMapping("info/{id}")
    public Result getSysUserInfoById(@PathVariable("id") Long sysUserId) {
        return adminSysUserService.getSysUserInfoById(sysUserId);
    }

    @PostMapping("add")
    public Result addSysUser(@RequestBody SysUserInfoVo sysUserInfoVo) {
        return adminSysUserService.addSysUser(sysUserInfoVo);
    }

    @PostMapping("update")
    public Result updateSysUser(@RequestBody SysUserInfoVo sysUserInfoVo) {
        return adminSysUserService.updateSysUser(sysUserInfoVo);
    }

    @PostMapping("delete/{id}")
    public Result deleteSysUser(@PathVariable("id") Long sysUserId) {
        return adminSysUserService.deleteSysUser(sysUserId);
    }

    @PostMapping("batch/delete")
    public Result batchDeleteSysUsers(@RequestBody List<Long> sysUserIds) {
        return adminSysUserService.batchDeleteSysUsers(sysUserIds);
    }

    @PostMapping("batch/update")
    public Result batchUpdateSysUsers(@RequestBody BatchUpdateSysUsersParam batchUpdateSysUsersParam) {
        return adminSysUserService.batchUpdateSysUsers(batchUpdateSysUsersParam);
    }
}
