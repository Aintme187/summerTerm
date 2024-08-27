package com.example.blogapi.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.blogapi.dao.pojo.Comment;
import com.example.blogapi.service.CommentsService;
import com.example.blogapi.vo.ErrorCode;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.CommentParam;
import com.example.blogapi.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")

    public Result comments(@PathVariable("id") Long articleId){

        return commentsService.commentsByArticleId(articleId);

    }
    // 降级处理方法
    public Result fallbackHandlerForComments(Long articleId, Throwable e) {
        // 返回预设的响应或错误信息
        return Result.fail(ErrorCode.IS_LIMITING_FLOW);
    }

    // 限流/熔断处理方法
    public Result blockHandlerForComments(Long articleId, BlockException ex) {
        return Result.fail(ErrorCode.IS_LIMITING_FLOW);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){

        return commentsService.comment(commentParam);
    }
}
