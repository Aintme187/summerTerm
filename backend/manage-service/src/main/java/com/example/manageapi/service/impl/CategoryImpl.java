package com.example.manageapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.manageapi.dao.mapper.CategoryMapper;
import com.example.manageapi.dao.pojo.Category;
import com.example.manageapi.service.CategoryService;
import com.example.manageapi.vo.CategoryVo;
import com.example.manageapi.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAllCategorys() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categories));
    }

    @Override
    public Result findAllDetail() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        //页面交互的对象
        return Result.success(categories);
    }

    public Result create(Category category) {
        categoryMapper.insert(category);
        return Result.success(category);
    }
    @Override
    public Result categoriesDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        //CategoryVo categoryVo = copy(category);
        return Result.success(category);
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for(Category category : categories){
            CategoryVo categoryVo = copy(category);
            categoryVoList.add(categoryVo);
        }
        return categoryVoList;
    }

    private CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
