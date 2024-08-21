package com.example.manageapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.manageapi.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 根据标签下文章数量查询热门标签
     * @param limit
     * @return
     */
    List<Long> findHotTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
