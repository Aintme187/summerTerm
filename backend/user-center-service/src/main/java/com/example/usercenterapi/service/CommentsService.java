package com.example.usercenterapi.service;

import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.CommentParam;

public interface CommentsService {


    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
