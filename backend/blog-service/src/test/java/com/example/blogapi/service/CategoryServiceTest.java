package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Category;
import com.example.blogapi.vo.CategoryVo;
import com.example.blogapi.vo.Result;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Test
    public void findCategoryByIdP() {
        CategoryVo categoryVo = categoryService.findCategoryById(1L);
        Assertions.assertNotNull(categoryVo);
    }

    @Test
    public void findCategoryByIdN() {
        assertThrows(IllegalArgumentException.class, () -> {
            CategoryVo categoryVo = categoryService.findCategoryById(0L);
        });

    }

    @Test
    public void findAllCategorysP() {
        Result allCategorys = categoryService.findAllCategorys();
        Object data = allCategorys.getData();
        List<Category> categories = (List<Category>)data;
        Assertions.assertNotEquals(categories.size(), 0);
    }

    @Test
    public void findAllDetail() {
        Result result = categoryService.findAllDetail();
        Object data = result.getData();
        List<Category> categories = (List<Category>)data;
        Assertions.assertNotEquals(categories.size(), 0);
    }

    @Test
    public void categoriesDetailByIdP() {
        Result result = categoryService.categoriesDetailById(1L);
        Category category = (Category)result.getData();
        Assertions.assertNotNull(category);
    }
    @Test
    public void categoriesDetailByIdN() {
        Result result = categoryService.categoriesDetailById(0L);
        Category category = (Category)result.getData();
        Assertions.assertNull(category);
    }
}