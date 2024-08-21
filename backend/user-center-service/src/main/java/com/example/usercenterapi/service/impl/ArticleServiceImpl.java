package com.example.usercenterapi.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogapi.dao.pojo.*;
import com.example.blogapi.service.*;
import com.example.usercenterapi.dao.mapper.*;
import com.example.usercenterapi.dao.pojo.*;
import com.example.usercenterapi.service.*;
import com.example.usercenterapi.utils.QiniuUtils;
import com.example.usercenterapi.utils.StringUtilsForTeaching;
import com.example.usercenterapi.utils.UserThreadLocal;
import com.example.blogapi.vo.*;
import com.example.usercenterapi.vo.*;
import com.example.usercenterapi.vo.params.ArticleParam;
import com.example.usercenterapi.vo.params.PageParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ArticleServiceImpl implements ArticleService {
    /**
     * 1.分页查询article数据库表
     * 2.根据创建权重和时间倒序排列
     * 3.将record转移到vo对象
     * 4.通过BeanUtils做同名属性数值复制
     */
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private FavoriteArticleMapper favoriteArticleMapper;
    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private ArticleMaterialMapper articleMaterialMapper;
    @Autowired
    private UserArticleLikeMapper userArticleLikeMapper;
    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {

        boolean isSelected = false;
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());

        //查询文章的参数 加上分类id，判断不为空 加上分类条件
        if (pageParams.getCategoryId() != null) {
            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
            isSelected = true;
        }

        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null){
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleIdList.size() > 0){
                isSelected = true;
                queryWrapper.in(Article::getId,articleIdList);
            }

        }
        if(pageParams.getFavoriteId() != null){
            Long favoriteId = pageParams.getFavoriteId();
            LambdaQueryWrapper<FavoriteArticle> favoriteArticleQueryWrapper = new LambdaQueryWrapper<>();
            favoriteArticleQueryWrapper.eq(FavoriteArticle::getFavoriteId, favoriteId);
            List<FavoriteArticle> favoriteArticles = favoriteArticleMapper.selectList(favoriteArticleQueryWrapper);
            for(FavoriteArticle favoriteArticle : favoriteArticles){
                articleIdList.add(favoriteArticle.getArticleId());

            }
            if (articleIdList.size() > 0){
                isSelected = true;
                queryWrapper.in(Article::getId,articleIdList);
            }

        }
        if(pageParams.getCategoryId() == null && pageParams.getTagId() == null && pageParams.getFavoriteId() == null){
            isSelected = true;
        }
        if(!isSelected){
            page.setSize(0L);
            page.setPages(0L);
        }
        queryWrapper.orderByDesc(Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();

        List<ArticleVo> articleVoList = copyList(records, true, true, false, true);
        System.out.println(articleVoList);
        return articleVoList;
    }



    @Override
    public Result articleHot(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCommentCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, true, true, false, false));
    }

    @Override
    public Result articleNew(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, true, true, false, false));
    }

    /**
     * 查看文章详情，查找出articleVo
     * @param articleId
     * @return
     */
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1.根据id查找出文章信息
         * 2.根据bodyId和categoryId做关联查询
         * 3.查看完文章后，阅读数量+1，但是由于写的时候不能读，增加系统耗时，性能低下
         * 4.利用线程池，把更新操作丢到线程池，不影响主线程
         */

        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true, true, true);

        //ThreadService -> updateViewCount
        threadService.updateViewCount(article);

        return Result.success(articleVo);
    }

    /**
     * 利用线程池完成点赞数量+1
     * @param articleId
     * @return
     */
    @Override
    public Result like(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        SysUser sysUser = UserThreadLocal.get();
        if(sysUser == null) sysUser = sysUserService.findUserById(1L);
        Long userId = sysUser.getId();
        UserArticleLike like = userArticleLikeMapper.findLike(userId, articleId);
        if(like != null) return Result.fail(666, "您已点赞过此博客");
        Article articleUpdate = new Article();
        articleUpdate.setLikeCounts(article.getLikeCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        //乐观锁
        queryWrapper.eq(Article::getLikeCounts, article.getLikeCounts());
        articleMapper.update(articleUpdate, queryWrapper);
        Article articleAfterLike = articleMapper.selectById(articleId);
        UserArticleLike userArticleLike = new UserArticleLike();
        userArticleLike.setUserId(userId);
        userArticleLike.setArticleId(articleId);
        userArticleLikeMapper.insert(userArticleLike);
        return Result.success(articleAfterLike);
    }

    @Override
    public Result myBlogs(Long userId, Long categoryId) {

        List<Article> articles = new ArrayList<>();
        if(categoryId == 0){
            articles = articleMapper.getMyBlogs(userId);
        }
        else{
            articles = articleMapper.getMyBlogsWithCategory(userId, categoryId);
        }
        List<ArticleVo> articleVoList = copyList(articles, true, true, false, true);
        return Result.success(articleVoList);
    }

    @Override
    public Result related(List<Tag> tags) {
        //System.out.println(tags);

        List<Long> tagIds = new ArrayList<>();
        for(Tag tag : tags){
            tagIds.add(tag.getId());
        }
        List<Article> articles = articleMapper.getRelated(tagIds);
        System.out.println(articles);
        return Result.success(articles);

    }

    @Override
    public Result hotByUser(Long userId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCommentCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.eq(Article::getAuthorId, userId);
        queryWrapper.last("limit " + 5);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(articles);

    }

    @Override
    public void test() {
        System.out.println("test");
    }


    /**
     * 1.添加进登录拦截器
     * 2.构建article添加
     * 3.添加article tag关联表
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam, MultipartFile multipartFile) {
        Article article = new Article();

        SysUser sysUser = UserThreadLocal.get();

        article.setAuthorId(sysUser.getId());
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
        Long articleId = article.getId();

        List<TagVo> tagVoList = articleParam.getTags();

        if(tagVoList != null){
            for(TagVo tagVo : tagVoList){
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tagVo.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(articleId);
        if(multipartFile != null){
            /**
             * 文件上传
             */
            String originalFilename = multipartFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
            boolean upload = qiniuUtils.upload(multipartFile, fileName);
            if(upload){
                // 插入记录
                ArticleMaterial material = new ArticleMaterial();
                material.setTime(LocalDateTime.now());
                material.setArticleId(article.getId());
                material.setUrl(fileName);
                material.setName(originalFilename);
                material.setSize(StringUtilsForTeaching.formatSize(multipartFile.getSize()));
                articleMaterialMapper.insert(material);
            }
        }

        return Result.success(articleVo);
    }
    private List<ArticleVo> copyList(List<Article> records, boolean withTag, boolean withAuthor, boolean withBody, boolean withCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, withTag, withAuthor, withBody, withCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean withTag, boolean withAuthor, boolean withBody, boolean withCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        /*
        Vo 的createTime是String类型无法转移,需要自己手动new DateTime，最后转为String类型
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        articleVo.setCreateDate(simpleDateFormat.format(new Date(System.currentTimeMillis())));
        System.out.println("here articleVo createData" + articleVo.getCreateDate());
        if(withTag){
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if(withAuthor){
            System.out.println("============================below are articleAuthor info");
            System.out.println("articleAuthor" + sysUserService.findUserById(article.getAuthorId()).getNickname());
            articleVo.setAuthor(sysUserService.findUserById(article.getAuthorId()).getNickname());
            SysUser sysUser = sysUserService.findUserById(article.getAuthorId());
            SysUserVo sysUserVo = new SysUserVo();
            BeanUtils.copyProperties(sysUser, sysUserVo);
            sysUserVo.setCreateDate(simpleDateFormat.format(new Date(System.currentTimeMillis())));
            articleVo.setAuthorInfo(sysUserVo);
        }
        if(withBody){
            Long bodyId = article.getBodyId();
            ArticleBodyVo body = findBodyById(bodyId);
            articleVo.setBody(body);
        }
        if(withCategory){
            Long categoryId = article.getCategoryId();
            CategoryVo categoryVo = categoryService.findCategoryById(categoryId);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }

    private ArticleBodyVo findBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
