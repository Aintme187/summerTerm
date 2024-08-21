package com.example.manageapi.service;

import com.example.manageapi.dao.pojo.Category;
import com.example.manageapi.vo.CategoryVo;
import com.example.manageapi.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAllCategorys();

    Result findAllDetail();

    Result categoriesDetailById(Long id);

    Result create(Category category);
}
