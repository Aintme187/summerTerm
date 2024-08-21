package com.example.usercenterapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.usercenterapi.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> getMyBlogs(Long id);

    List<Article> getRelated(List<Long> tagIds);

    List<Article> getMyBlogsWithCategory(Long userId, Long categoryId);
}
