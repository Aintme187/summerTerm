package com.example.manageapi.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "user-center-service")
public interface SysuserClient {

    @GetMapping("/sysUser/findByAccount")
    SysUser findByAccount(@RequestParam("account") String account);

    @GetMapping("/sysUser/selectById")
    SysUser selectById(@RequestParam("id") Long id);

    @GetMapping("/sysUser/selectPage")
    Page<SysUser> selectPage(@RequestParam AdminPageParam adminPageParam, QueryWrapper<SysUser> queryWrapper);

    @PostMapping("/sysUser/insert")
    void insert(@RequestParam SysUser sysUser);

    @PutMapping("/sysUser/updateById")
    void updateById(@RequestParam SysUser sysUser);

    @DeleteMapping("/sysUser/deleteById")
    Result deleteById(@RequestParam Long id);

    @GetMapping("/sysUser/selectBatchIds")
    List<SysUser> selectBatchIds(@RequestParam List<Long> ids);

    @DeleteMapping("/sysUser/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids);

    @PutMapping("/sysUser/update")
    void update(@RequestParam SysUser sysUser, LambdaUpdateWrapper<SysUser> queryWrapper);
}
