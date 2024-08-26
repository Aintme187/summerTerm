package com.example.usercenterapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenterapi.dao.dto.TryAdminPageParam;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.service.SysUserService;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.AdminPageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ddddm
 **/
@RestController
@RequestMapping("sysUser")
@CrossOrigin
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    @GetMapping("/findByAccount")
    @CrossOrigin
    public SysUser findByAccount(@RequestParam String account) {
        return sysUserService.findUserByAccount(account);
    }

    @GetMapping("/selectById")
    public SysUser selectById(@RequestParam("id") Long id){
        return sysUserService.selectById(id);
    }

    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody AdminPageParam adminPageParam) {
        return sysUserService.selectPage(adminPageParam);
    }

    @PostMapping("/insert")
    public void insert(@RequestBody SysUser sysUser) {
        sysUserService.insert(sysUser);
    }

    @PutMapping("/updateById")
    public void updateById(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
    }

    @DeleteMapping("/deleteById")
    Result deleteById(@RequestParam Long id) {
        return sysUserService.deleteById(id);
    }

    @GetMapping("/selectBatchIds")
    List<SysUser> selectBatchIds(@RequestParam List<Long> ids) {
        return sysUserService.selectBatchIds(ids);
    }

    @DeleteMapping("/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids) {
        return sysUserService.deleteBatchIds(ids);
    }

    @PutMapping("/update")
    void update(@RequestBody SysUser sysUser, LambdaUpdateWrapper<SysUser> queryWrapper) {
        sysUserService.update(sysUser, queryWrapper);
    }
}
