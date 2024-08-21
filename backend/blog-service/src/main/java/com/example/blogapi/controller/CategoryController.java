package com.example.blogapi.controller;

import com.example.blogapi.dao.pojo.Category;
import com.example.blogapi.service.CategoryService;
import com.example.blogapi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public Result Categorys(){
        return categoryService.findAllCategorys();
    }

    @GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }

    @PostMapping("create")
    public Result create(@RequestBody Category category){
        return categoryService.create(category);
    }
}
