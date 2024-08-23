package com.example.teachapi.client;

import com.example.teachapi.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-center-service")
public interface sysUserClient {

    @GetMapping("/users/getMyInfo")
    Result queryInfo(@RequestParam("id") Long id);
}
