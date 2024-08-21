package com.example.manageapi.service;

import com.example.manageapi.dao.pojo.Tag;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.TagVo;

import java.util.List;
public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    List<TagVo> hot(int limit);

    Result findAllTags();

    Result findAllDetail();

    Result findDetailById(Long id);

    Result create(Tag tag);
}
