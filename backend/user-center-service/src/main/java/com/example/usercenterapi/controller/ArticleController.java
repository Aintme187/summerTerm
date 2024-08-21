package com.example.usercenterapi.controller;

import com.example.usercenterapi.common.aop.LogAnnotation;
import com.example.usercenterapi.common.cache.Cache;
import com.example.usercenterapi.dao.pojo.Tag;
import com.example.usercenterapi.service.ArticleService;
import com.example.usercenterapi.vo.ArticleVo;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.ArticleParam;
import com.example.usercenterapi.vo.params.PageParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// 指定交互的数据格式都是json
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //Result是统一结果返回
    @PostMapping
    @LogAnnotation(module="文章", operator="获取文章列表")
    public Result articles(@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        List<ArticleVo> articles = articleService.listArticlesPage(pageParams);
        System.out.println("articles debug ~~~~" + articles);
        return Result.success(articles);
    }

    @GetMapping("test")
    public Result test(){
        articleService.test();
        return Result.success(null);
    }

    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000,name = "articlesHot")
    public Result articlesHot(){
        int limit = 10;
        return articleService.articleHot(limit);
    }

    @PostMapping("new")
    public Result articleNew(){
        int limit = 5;
        return articleService.articleNew(limit);
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping(value = "publish", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Result publish(@RequestPart("articleParam") String articleParamJson,
                          @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws JsonProcessingException {
        ArticleParam articleParam = new ObjectMapper().readValue(articleParamJson, ArticleParam.class);
        return articleService.publish(articleParam, multipartFile);
    }

    @PostMapping("like/{id}")
    public Result like(@PathVariable("id") Long articleId){
        return articleService.like(articleId);
    }

    @PostMapping("myBlogs/{id}/{categoryId}")
    public Result myBlogs(@PathVariable("id") Long userId, @PathVariable("categoryId") Long categoryId){
        return articleService.myBlogs(userId, categoryId);
    }

    @PostMapping("related")
    public Result related(@RequestBody List<Tag> tags){
        return articleService.related(tags);
    }

    @PostMapping("hot/{id}")
    public Result hotByUser(@PathVariable("id") Long userId){
        return articleService.hotByUser(userId);
    }
}