package com.example.manageapi.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateSysUsersParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "user-center-service")
@Component
public interface SysuserClient {

    @GetMapping("/sysUser/findByAccount")
    SysUser findByAccount(@RequestParam("account") String account);

    @GetMapping("/sysUser/selectById")
    SysUser selectById(@RequestParam("id") Long id);

    @PostMapping("/sysUser/selectPage")
    Result selectPage(@RequestBody AdminPageParam adminPageParam);

    @PostMapping("/sysUser/insert")
    void insert(@RequestBody SysUser sysUser);

    @PutMapping("/sysUser/updateById")
    void updateById(@RequestBody SysUser sysUser);

    @DeleteMapping("/sysUser/deleteById")
    Result deleteById(@RequestParam Long id);

    @GetMapping("/sysUser/selectBatchIds")
    List<SysUser> selectBatchIds(@RequestParam List<Long> ids);

    @DeleteMapping("/sysUser/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids);

    @PutMapping("/sysUser/update")
    Result update(@RequestBody BatchUpdateSysUsersParam batchUpdateSysUsersParam);
}
