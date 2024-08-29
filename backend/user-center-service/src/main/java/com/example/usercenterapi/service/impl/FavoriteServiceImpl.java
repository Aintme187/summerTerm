package com.example.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.example.usercenterapi.dao.mapper.FavoriteArticleMapper;
import com.example.usercenterapi.dao.mapper.FavoriteMapper;
import com.example.usercenterapi.dao.mapper.UserFavoriteMapper;
import com.example.usercenterapi.dao.pojo.Favorite;
import com.example.usercenterapi.dao.pojo.FavoriteArticle;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.dao.pojo.UserFavorite;
import com.example.usercenterapi.service.FavoriteService;
import com.example.usercenterapi.utils.UserThreadLocal;
import com.example.usercenterapi.vo.FavoriteVo;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.FavoriteParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private UserFavoriteMapper userFavoriteMapper;
    @Override
    public Result list() {
        return Result.success(favoriteMapper.selectList(new LambdaQueryWrapper<>()));
    }



    /**
     * 用户创建收藏夹
     * @param favorite
     * @return
     */
    @Override
    public Result create(Favorite favorite) {
        favoriteMapper.insert(favorite);
        SysUser sysUser = UserThreadLocal.get();
        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setUserId(sysUser.getId());
        userFavorite.setFavoriteId(favorite.getId());
        userFavoriteMapper.insert(userFavorite);
        return Result.success(favorite);
    }

}
