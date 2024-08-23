package com.example.blogapi;

import com.example.blogapi.dao.mapper.ArticleMapper;
import com.example.blogapi.dao.pojo.Article;
import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.dao.pojo.Tag;
import com.example.blogapi.service.AdminSysUserService;
import com.example.blogapi.service.ArticleService;
import com.example.blogapi.service.SysUserService;
import com.example.blogapi.vo.ArticleVo;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.SysUserInfoVo;
import com.example.blogapi.vo.params.ArticleParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class BlogApiApplicationTests {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private AdminSysUserService adminSysUserService;
    @Autowired
    private SysUserService sysUserService;

//    /**
//     * 展示文章
//     */
//    @Test
//    public void listArticlesPagePositive() {
//        PageParams pageParams = new PageParams();
//        pageParams.setPage(1);
//        pageParams.setPageSize(3);
//
//        List<ArticleVo> articleVoList = articleService.listArticlesPage(pageParams);
//        assertNotNull(articleVoList);
//    }
//
//    @Test
//    public void listArticlesPageNegative() {
//        PageParams pageParams = new PageParams();
//        pageParams.setPage(1);
//        pageParams.setPageSize(0);
//
//        List<ArticleVo> articleVoList = articleService.listArticlesPage(pageParams);
//
//        Assertions.assertEquals(articleVoList.size(), 0);
//    }

    /**
     * 热点文章
     */
    @Test
    public void articleHotPositive() {

        int limit = 5;
        Result result = articleService.articleHot(limit);
        List<Article> data = (ArrayList<Article>) result.getData();
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void articleHotNegative() {

        int limit = 0;
        Result result = articleService.articleHot(limit);
        List<Article> data = (ArrayList<Article>) result.getData();
        Assertions.assertEquals(data.size(), 0);
    }

    /**
     * 最新文章
     */
    @Test
    public void articleNewPositive() {
        int limit = 5;
        Result result = articleService.articleNew(limit);
        List<Article> data = (ArrayList<Article>) result.getData();
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void articleNewNegative() {

        int limit = 0;
        Result result = articleService.articleNew(limit);
        List<Article> data = (ArrayList<Article>) result.getData();
        Assertions.assertEquals(data.size(), 0);
    }

    /**
     * 根据id查找文章
     */
    @Test
    public void findArticleByIdPositive() {
        Long id = 5L;
        Result result = articleService.findArticleById(id);
        ArticleVo articleVo = (ArticleVo) result.getData();
        assertNotNull(articleVo);
    }

    @Test
    public void findArticleByIdNegative() {
        Long id = -1L;

        assertThrows(IllegalArgumentException.class, () -> {
            Result result = articleService.findArticleById(id);
        });


    }

    /**
     * 发布文章
     */
    @Test
    public void publishPositive() {


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
    public void publishNegative() {
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

//    /**
//     * 点赞
//     */
//    @Test
//    public void likePositive() {
//        Result res = articleService.findArticleById(5L);
//        ArticleVo article = (ArticleVo) res.getData();
//
//        Result result = articleService.like(5L);
//        Article articleAfterLike = (Article) result.getData();
//
//        Assertions.assertEquals(articleAfterLike.getLikeCounts(), article.getLikeCounts() + 1);
//    }
//    @Test
//    public void likeNegative() {
//        assertThrows(NullPointerException.class, () -> {
//            Result res = articleService.findArticleById(1L);
//            ArticleVo article = (ArticleVo) res.getData();
//
//            Result result = articleService.like(-1L);
//            Article articleAfterLike = (Article) result.getData();
//        });
//
//    }
//
//    /**
//     * 查询我的博客
//     */
//    @Test
//    public void myBlogsP() {
//        Result result = articleService.myBlogs(1L, 0L);
//        List<ArticleVo> articleVoList = (ArrayList<ArticleVo>) result.getData();
//        Assertions.assertNotEquals(articleVoList.size(), 0);
//
//    }
//    @Test
//    public void myBlogsN() {
//        Result result = articleService.myBlogs(-1L, 0L);
//        List<ArticleVo> articleVoList = (ArrayList<ArticleVo>) result.getData();
//        Assertions.assertEquals(articleVoList.size(), 0);
//
//    }

    /**
     * 查找相关博客
     */
    @Test
    public void relatedPos() {
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
    public void relatedNeg() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setTagName("123");
        tags.add(tag1);
        Result result = articleService.related(tags);
        List<Article> articles = (ArrayList<Article>) result.getData();
        Assertions.assertEquals(articles.size(), 0);
    }

//    @Test
//    public void hotByUserPos() {
//        Result result = articleService.hotByUser(1L);
//        List<Article> data = (ArrayList<Article>)result.getData();
//        Assertions.assertNotEquals(data.size(), 0);
//    }
//    @Test
//    public void hotByUserNeg() {
//        Result result = articleService.hotByUser(2L);
//        List<Article> data = (ArrayList<Article>)result.getData();
//        Assertions.assertEquals(data.size(), 0);
//    }

    /**
     * 以下所有的adminSysUserService测试有先后的逻辑关系，请不要单个测试
     */
    @Test
    @Order(0)
    public void addSysUserP() {
        SysUser user = sysUserService.findUser("testtest", "testtest");
        if (user != null) {
            adminSysUserService.deleteSysUser(user.getId());
        }
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setAccount("testtest");
        sysUserInfoVo.setPassword("testtest");
        sysUserInfoVo.setNickname("testtest");
        sysUserInfoVo.setPermission("administer");
        Result result = adminSysUserService.addSysUser(sysUserInfoVo);
        user = sysUserService.findUser("testtest", DigestUtils.md5Hex("testtest" + "salt"));
        Assertions.assertNotNull(user);
    }

    @Test
    @Order(1)
    public void addSysUserN() {
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setAccount("test"); //illegal account
        sysUserInfoVo.setPassword("test");
        sysUserInfoVo.setPermission("test");
        Result result = adminSysUserService.addSysUser(sysUserInfoVo);
        Assertions.assertNotEquals(result.getCode(), 200);
    }
//
//    @Test
//    @Order(2)
//    public void listSysUserPageP() {
//        AdminPageParam adminPageParam = new AdminPageParam();
//        adminPageParam.setPageSize(3L);
//        adminPageParam.setPage(1L);
//        adminPageParam.setFilterDataList(new ArrayList<>());
//        Result result = adminSysUserService.listSysUserPage(adminPageParam);
//        Assertions.assertEquals(result.getCode(),200);
//        Assertions.assertNotEquals(((AdminSysUserVo) result.getData()).getAdminSysUserCount(), 0);
//    }
//
//    @Test
//    @Order(3)
//    public void listSysUserPageN() {
//        AdminPageParam adminPageParam = new AdminPageParam();
//        adminPageParam.setPageSize(3L);
//        adminPageParam.setPage(-1L);
//        adminPageParam.setFilterDataList(new ArrayList<>());
//        Result result = adminSysUserService.listSysUserPage(adminPageParam);
//        Assertions.assertNotEquals(result.getCode(), 200);
//    }

    @Test
    @Order(4)
    public void getSysUserInfoByIdP() {
        Long sysUserId = sysUserService.findUser("testtest", DigestUtils.md5Hex("testtest" + "salt")).getId();
        Result result = adminSysUserService.getSysUserInfoById(sysUserId);
        Assertions.assertEquals(((SysUserInfoVo) result.getData()).getAccount(), "testtest");
    }

    @Test
    @Order(5)
    public void getSysUserInfoByIdN() {
        Result result = adminSysUserService.getSysUserInfoById(-1L);
        Assertions.assertNotEquals(result.getCode(), 200);
    }

    @Test
    @Order(6)
    public void updateSysUserP() {
        Long sysUserId = sysUserService.findUser("testtest", DigestUtils.md5Hex("testtest" + "salt")).getId();
        String newNickname = "testtesttest";
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setAccount("testtest");
        sysUserInfoVo.setNickname(newNickname);
        adminSysUserService.updateSysUser(sysUserInfoVo);
        Result result = adminSysUserService.getSysUserInfoById(sysUserId);
        Assertions.assertEquals(((SysUserInfoVo) result.getData()).getNickname(), "testtesttest");
    }

    @Test
    @Order(7)
    public void updateSysUserN() {
        Long sysUserId = sysUserService.findUser("testtest", DigestUtils.md5Hex("testtest" + "salt")).getId();
        String newPassword = "test"; //illegal password
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        sysUserInfoVo.setAccount("testtest");
        sysUserInfoVo.setPassword(newPassword);
        Result result = adminSysUserService.updateSysUser(sysUserInfoVo);
        Assertions.assertNotEquals(result.getCode(), 200);
    }

    @Test
    @Order(8)
    public void deleteSysUserP() {
        Long sysUserId = sysUserService.findUser("testtest", DigestUtils.md5Hex("testtest" + "salt")).getId();
        adminSysUserService.deleteSysUser(sysUserId);
        Result result = adminSysUserService.getSysUserInfoById(sysUserId);
        Assertions.assertNull(result.getData());
    }

    @Test
    @Order(9)
    public void deleteSysUserN() {
        Result result = adminSysUserService.deleteSysUser(-1L);
        Assertions.assertNotEquals(result.getCode(), 200);
    }

}
