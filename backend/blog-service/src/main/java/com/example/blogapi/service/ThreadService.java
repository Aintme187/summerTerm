package com.example.blogapi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogapi.dao.mapper.ArticleMapper;
import com.example.blogapi.dao.mapper.UserArticleLikeMapper;
import com.example.blogapi.dao.pojo.Article;

import com.example.blogapi.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserArticleLikeMapper userArticleLikeMapper;

    //这表示该方法将异步执行，并且会使用在Spring容器中定义的名为 "taskExecutor" 的 Executor bean
    @Async("taskExecutor")
    public void updateViewCount(Article article) {

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        //乐观锁
        queryWrapper.eq(Article::getViewCounts, article.getViewCounts());
        articleMapper.update(articleUpdate, queryWrapper);

        try {
            //Thread
            Thread.sleep(5000);
            System.out.println("=========view count update has been finished===========");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
