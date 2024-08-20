package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Tag;
import com.example.blogapi.vo.ArticleVo;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.ArticleParam;
import com.example.blogapi.vo.params.PageParams;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */

    List<ArticleVo> listArticlesPage(PageParams pageParams);

    Result articleHot(int limit);

    Result articleNew(int limit);

    Result findArticleById(Long articleId);

    Result publish(ArticleParam articleParam, MultipartFile multipartFile);

    Result like(Long articleId);

    Result myBlogs(Long userId, Long categoryId);

    Result related(List<Tag> tags);

    Result hotByUser(Long userId);
}
