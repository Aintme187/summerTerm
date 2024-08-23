package com.example.manageapi.client;

import com.example.manageapi.dao.dto.Category;
import com.example.manageapi.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "blog-service")
public interface BlogClient {
    @PostMapping("/categorys/creat")
    Result insert(@RequestParam Category category);
}
