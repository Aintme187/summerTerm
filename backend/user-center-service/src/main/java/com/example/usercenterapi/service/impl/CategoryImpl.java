package com.example.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.usercenterapi.dao.mapper.CategoryMapper;
import com.example.usercenterapi.dao.pojo.Category;
import com.example.usercenterapi.service.CategoryService;
import com.example.usercenterapi.vo.CategoryVo;
import com.example.usercenterapi.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public Result create(Category category) {
        categoryMapper.insert(category);
        return Result.success(category);
    }

}
