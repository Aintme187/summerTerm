package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Category;
import com.example.blogapi.vo.CategoryVo;
import com.example.blogapi.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAllCategorys();

    Result findAllDetail();

    Result categoriesDetailById(Long id);

    Result create(Category category);
}
