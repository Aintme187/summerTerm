package com.example.blogapi.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.example.blogapi.common.aop.LogAnnotation;
import com.example.blogapi.common.cache.Cache;
import com.example.blogapi.dao.pojo.Material;
import com.example.blogapi.dao.pojo.Tag;
import com.example.blogapi.service.ArticleService;
import com.example.blogapi.service.StudentLearningService;
import com.example.blogapi.vo.ArticleVo;
import com.example.blogapi.vo.ErrorCode;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.ArticleParam;
import com.example.blogapi.vo.params.PageParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// 指定交互的数据格式都是json
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private StudentLearningService studentLearningService;

    @Autowired
    private ArticleService articleService;

    //Result是统一结果返回
    @PostMapping
    @LogAnnotation(module="文章", operator="获取文章列表")
    @Cache(expire = 5 * 60 * 1000,name = "articles")
    public Result articles(@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        List<ArticleVo> articles = articleService.listArticlesPage(pageParams);
        System.out.println("articles debug ~~~~" + articles);
        return Result.success(articles);
    }


    //降级处理方法
    public Result fallbackHandlerForArticles(PageParams pageParams, Throwable e) {
        // 可以返回一个预设的响应或错误信息
        return Result.fail(ErrorCode.IS_LIMITING_FLOW);
    }
    // 限流/熔断处理方法
    public Result blockHandlerForArticles(PageParams pageParams, BlockException ex) {
        return Result.fail(ErrorCode.IS_LIMITING_FLOW);
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
    @Cache(expire = 5 * 60 * 1000,name = "articleNew")
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
    @GetMapping("/materials")
    public List<Material> getMaterialByArticleId(@RequestParam Long articleId) {
        return studentLearningService.getMaterialByArticleId(articleId);
    }

    /**
     * 获取课程资料打开链接
     * @param materialId
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<Result> downloadMaterial(@RequestParam Long materialId) {
        ResponseEntity<Result> res = studentLearningService.getMaterialDownloadUrl(materialId);
        return res;
    }
    @GetMapping("/materials/download")
    public ResponseEntity<Result> downloadMaterialForArticle(@RequestParam Long materialId) {
        return studentLearningService.downloadMaterialForArticle(materialId);
    }
}