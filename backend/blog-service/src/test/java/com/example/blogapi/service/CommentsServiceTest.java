package com.example.blogapi.service;

import com.example.blogapi.vo.CommentVo;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class CommentsServiceTest {
    @Autowired
    private CommentsService commentsService;
    @Test
    public void commentsByArticleId() {
        Result result = commentsService.commentsByArticleId(1L);
        Assertions.assertNotEquals((List<CommentVo>)result.getData(), 0);
    }

    @Test
    public void comment() {
    }
}