package com.example.blogapi.service;

import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.TagVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TagServiceTest {

    @Autowired
    private TagService tagService;
    @Test
    void findTagsByArticleIdP() {
        List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(1L);
        Assertions.assertNotEquals(tagsByArticleId.size(), 0);
    }
    @Test
    void findTagsByArticleIdN() {
        List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(0L);
        Assertions.assertEquals(tagsByArticleId.size(), 0);
    }

    @Test
    void hot() {
        List<TagVo> hot = tagService.hot(5);
        Assertions.assertNotEquals(hot.size(), 0);
    }

    @Test
    void findAllTags() {
        Result allTags = tagService.findAllTags();
        Assertions.assertNotNull(allTags.getData());
    }

    @Test
    void findAllDetail() {
        Result allDetail = tagService.findAllDetail();
        Assertions.assertNotNull(allDetail.getData());
    }

    @Test
    void findDetailByIdP() {
        Result detailById = tagService.findDetailById(5L);
        Assertions.assertNotNull(detailById.getData());
    }
    @Test
    void findDetailByIdN() {
        Result detailById = tagService.findDetailById(0L);
        Assertions.assertNull(detailById.getData());
    }

}