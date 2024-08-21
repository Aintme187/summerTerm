package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Tag;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.TagVo;
import java.util.List;
public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    List<TagVo> hot(int limit);

    Result findAllTags();

    Result findAllDetail();

    Result findDetailById(Long id);

    Result create(Tag tag);
}
