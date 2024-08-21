package com.example.manageapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.manageapi.dao.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
    List<Favorite> selectListByUserId(Long userId);
}
