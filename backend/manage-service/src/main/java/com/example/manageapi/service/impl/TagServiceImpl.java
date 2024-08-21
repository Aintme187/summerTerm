package com.example.manageapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.manageapi.dao.mapper.TagMapper;
import com.example.manageapi.dao.pojo.Tag;
import com.example.manageapi.service.TagService;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }
    public Result create(Tag tag) {
        tagMapper.insert(tag);
        return Result.success(tag);
    }

    @Override
    public List<TagVo> hot(int limit) {

        List<Long> hotsTagIds = tagMapper.findHotTagIds(limit);
        if (CollectionUtils.isEmpty(hotsTagIds)){
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(hotsTagIds);
        return copyList(tagList);
    }

    @Override
    public Result findAllTags() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        List<TagVo> tagVoList = copyList(tags);
        return Result.success(tagVoList);
    }

    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return Result.success(tags);
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(tag);
    }

    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
}
