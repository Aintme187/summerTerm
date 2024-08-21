package com.example.manageapi.service;

import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.CommentParam;

public interface CommentsService {


    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
