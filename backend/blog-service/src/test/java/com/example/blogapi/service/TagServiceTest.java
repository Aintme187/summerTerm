package com.example.blogapi.service;

import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.TagVo;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Test
    public void findTagsByArticleIdP() {
        List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(1L);
        Assertions.assertNotEquals(tagsByArticleId.size(), 0);
    }
    @Test
    public void findTagsByArticleIdN() {
        List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(0L);
        Assertions.assertEquals(tagsByArticleId.size(), 0);
    }

    @Test
    public void hot() {
        List<TagVo> hot = tagService.hot(5);
        Assertions.assertNotEquals(hot.size(), 0);
    }

    @Test
    public void findAllTags() {
        Result allTags = tagService.findAllTags();
        Assertions.assertNotNull(allTags.getData());
    }

    @Test
    public void findAllDetail() {
        Result allDetail = tagService.findAllDetail();
        Assertions.assertNotNull(allDetail.getData());
    }

    @Test
    public void findDetailByIdP() {
        Result detailById = tagService.findDetailById(5L);
        Assertions.assertNotNull(detailById.getData());
    }
    @Test
    public void findDetailByIdN() {
        Result detailById = tagService.findDetailById(0L);
        Assertions.assertNull(detailById.getData());
    }

}