package com.example.teachapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachapi.dao.pojo.UserArticleLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserArticleLikeMapper extends BaseMapper<UserArticleLike> {

    @Select("select id from user_article_like where user_id = #{userId} and article_id = #{articleId}")
    UserArticleLike findLike(Long userId, Long articleId);
}
