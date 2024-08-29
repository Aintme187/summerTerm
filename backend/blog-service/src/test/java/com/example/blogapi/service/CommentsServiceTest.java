package com.example.blogapi.service;

import com.beust.ah.A;
import com.example.blogapi.vo.CommentVo;
import com.example.blogapi.vo.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class CommentsServiceTest {
    @Autowired
    private CommentsService commentsService;
    @Test
    void commentsByArticleIdP() {
        Result result = commentsService.commentsByArticleId(1L);
        Assertions.assertNotEquals((List<CommentVo>)result.getData(), 0);
    }
    @Test
    void commentsByArticleIdN() {
        Result result = commentsService.commentsByArticleId(0L);
        Assertions.assertEquals(((List<CommentVo>)result.getData()).size(), 0);
    }

}