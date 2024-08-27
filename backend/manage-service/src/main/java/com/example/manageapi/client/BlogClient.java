package com.example.manageapi.client;

import com.example.manageapi.dao.dto.Category;
import com.example.manageapi.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "blog-service")
public interface BlogClient {
    @PostMapping("/categorys/create")
    Result insert(@RequestBody Category category);
}
