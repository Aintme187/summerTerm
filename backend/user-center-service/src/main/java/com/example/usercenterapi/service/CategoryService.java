package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.Category;
import com.example.usercenterapi.vo.CategoryVo;
import com.example.usercenterapi.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAllCategorys();

    Result findAllDetail();

    Result categoriesDetailById(Long id);

    Result create(Category category);
}
