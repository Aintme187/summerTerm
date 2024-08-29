package com.example.teachapi.client;

import com.example.teachapi.dao.pojo.ArticleMaterial;
import com.example.teachapi.dao.pojo.Material;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "blog-service")
public interface BlogClient {
    @GetMapping("/articles/materials")
    List<Material> getMaterialByArticleId(@RequestParam Long articleId);

    @GetMapping("/articles/material/{id}")
    ArticleMaterial selectMaterialById(@PathVariable Long id);
}
