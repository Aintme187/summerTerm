package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.Tag;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.TagVo;

import java.util.List;
public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    List<TagVo> hot(int limit);

    Result findAllTags();

    Result findAllDetail();

    Result findDetailById(Long id);

    Result create(Tag tag);
}
