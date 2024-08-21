package com.example.teachapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachapi.dao.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<Favorite> selectListByUserId(Long userId);
}
