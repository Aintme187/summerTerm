package com.example.blogapi.service;

import com.example.blogapi.dao.mapper.ArticleMapper;
import com.example.blogapi.dao.pojo.Article;
import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.dao.pojo.Tag;
import com.example.blogapi.utils.UserThreadLocal;
import com.example.blogapi.vo.ArticleVo;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.ArticleParam;
import com.example.blogapi.vo.params.PageParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 展示文章
     */
    @Test
    void listArticlesPagePositive() {
        PageParams pageParams = new PageParams();
        pageParams.setPage(1);
        pageParams.setPageSize(3);

        List<ArticleVo> articleVoList = articleService.listArticlesPage(pageParams);
        Assertions.assertNotNull(articleVoList);
    }
    @Test
    void listArticlesPageNegative() {
        PageParams pageParams = new PageParams();
        pageParams.setPage(1);
        pageParams.setPageSize(0);

        List<ArticleVo> articleVoList = articleService.listArticlesPage(pageParams);

        Assertions.assertEquals(articleVoList.size(), 0);
    }

    /**
     * 热点文章
     */
    @Test
    void articleHotPositive() {

        int limit = 5;
        Result result = articleService.articleHot(limit);
        List<Article> data = (ArrayList<Article>)result.getData();
        Assertions.assertNotEquals(data.size(), 0);
    }
    @Test
    void articleHotNegative() {

        int limit = 0;
        Result result = articleService.articleHot(limit);
        List<Article> data = (ArrayList<Article>)result.getData();
        Assertions.assertEquals(data.size(), 0);
    }

    /**
     * 最新文章
     */
    @Test
    void articleNewPositive() {
        int limit = 5;
        Result result = articleService.articleNew(limit);
        List<Article> data = (ArrayList<Article>)result.getData();
        Assertions.assertNotEquals(data.size(), 0);
    }
    @Test
    void articleNewNegative() {

        int limit = 0;
        Result result = articleService.articleNew(limit);
        List<Article> data = (ArrayList<Article>)result.getData();
        Assertions.assertEquals(data.size(), 0);
    }

    /**
     * 根据id查找文章
     */
    @Test
    void findArticleByIdPositive() {
        Long id = 1L;
        Result result = articleService.findArticleById(id);
        ArticleVo articleVo = (ArticleVo)result.getData();
        Assertions.assertNotNull(articleVo);
    }
    @Test
    void findArticleByIdNegative() {
        Long id = -1L;

        assertThrows(IllegalArgumentException.class, () -> {
            Result result = articleService.findArticleById(id);
        });


    }
    /**
     * 发布文章
     */
    @Test
    void publishPositive() {


        Article article = new Article();
        article.setCreateDate(new Date(System.currentTimeMillis()));
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setWeight(0);
        article.setBodyId(-1L);
        article.setLikeCounts(0);
        articleMapper.insert(article);
        assertNotNull(article.getId());
    }
    @Test
    void publishNegative() {
        assertThrows(NullPointerException.class, () -> {
            ArticleParam articleParam = new ArticleParam();
            articleParam.setId(1L);

            Article article = new Article();

            article.setSummary(articleParam.getSummary());
            article.setTitle(articleParam.getTitle());
            article.setCategoryId(articleParam.getCategory().getId());
            article.setCreateDate(new Date(System.currentTimeMillis()));
            article.setCommentCounts(0);
            article.setViewCounts(0);
            article.setWeight(0);
            article.setBodyId(-1L);
            article.setLikeCounts(0);
            articleMapper.insert(article);
            assertNotNull(article.getId());
        });

    }

    /**
     * 点赞
     */
    @Test
    void likePositive() {
        Result res = articleService.findArticleById(1L);
        ArticleVo article = (ArticleVo) res.getData();

        Result result = articleService.like(1L);
        Article articleAfterLike = (Article) result.getData();

        Assertions.assertEquals(articleAfterLike.getLikeCounts(), article.getLikeCounts() + 1);
    }
    @Test
    void likeNegative() {
        assertThrows(NullPointerException.class, () -> {
            Result res = articleService.findArticleById(1L);
            ArticleVo article = (ArticleVo) res.getData();

            Result result = articleService.like(-1L);
            Article articleAfterLike = (Article) result.getData();
        });

    }

    /**
     * 查询我的博客
     */
    @Test
    void myBlogsP() {
        Result result = articleService.myBlogs(1L, 0L);
        List<ArticleVo> articleVoList = (ArrayList<ArticleVo>) result.getData();
        Assertions.assertNotEquals(articleVoList.size(), 0);

    }
    @Test
    void myBlogsN() {
        Result result = articleService.myBlogs(-1L, 0L);
        List<ArticleVo> articleVoList = (ArrayList<ArticleVo>) result.getData();
        Assertions.assertEquals(articleVoList.size(), 0);

    }

    /**
     * 查找相关博客
     */
    @Test
    void relatedPos() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(7L);
        tag1.setTagName("123");
        tags.add(tag1);
        Result result = articleService.related(tags);
        List<Article> articles = (ArrayList<Article>) result.getData();
        Assertions.assertNotEquals(articles.size(), 0);
    }
    @Test
    void relatedNeg() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setTagName("123");
        tags.add(tag1);
        Result result = articleService.related(tags);
        List<Article> articles = (ArrayList<Article>) result.getData();
        Assertions.assertEquals(articles.size(), 0);
    }

    /**
     *
     */
    @Test
    void hotByUserPos() {
        Result result = articleService.hotByUser(1L);
        List<Article> data = (ArrayList<Article>)result.getData();
        Assertions.assertNotEquals(data.size(), 0);
    }
    @Test
    void hotByUserNeg() {
        Result result = articleService.hotByUser(2L);
        List<Article> data = (ArrayList<Article>)result.getData();
        Assertions.assertEquals(data.size(), 0);
    }
}