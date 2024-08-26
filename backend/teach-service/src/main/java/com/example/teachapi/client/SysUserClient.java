package com.example.teachapi.client;

import com.example.teachapi.dao.dto.SysUser;
import com.example.teachapi.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-center-service")
public interface SysUserClient {

    @GetMapping("/users/getMyInfo")
    Result queryInfo(@RequestParam("id") Long id);

    @GetMapping("/selectById")
    SysUser selectById(@RequestParam("id") Long id);
}
