package com.example.blogapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogapi.dao.mapper.FavoriteArticleMapper;
import com.example.blogapi.dao.mapper.FavoriteMapper;
import com.example.blogapi.dao.mapper.UserFavoriteMapper;
import com.example.blogapi.dao.pojo.Favorite;
import com.example.blogapi.dao.pojo.FavoriteArticle;
import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.dao.pojo.UserFavorite;
import com.example.blogapi.service.FavoriteService;
import com.example.blogapi.utils.UserThreadLocal;
import com.example.blogapi.vo.FavoriteVo;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.FavoriteParam;
import org.apache.catalina.User;
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
    @Autowired
    private FavoriteArticleMapper favoriteArticleMapper;
    @Override
    public Result list() {
        return Result.success(favoriteMapper.selectList(new LambdaQueryWrapper<>()));
    }

    @Override
    public Result listById(Long id) {
        Favorite favorite = favoriteMapper.selectById(id);
        return Result.success(favorite);
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

    @Override
    public Result showFavorite() {
        SysUser sysUser = UserThreadLocal.get();
        Long userId = sysUser.getId();
        List<Favorite> favoriteList = favoriteMapper.selectListByUserId(userId);

        ArrayList<FavoriteVo> favoriteVoList = new ArrayList<>();
        for(Favorite favorite : favoriteList){
            FavoriteVo favoriteVo = new FavoriteVo();
            favoriteVo.setId(favorite.getId());
            favoriteVo.setFavoriteName(favorite.getName());
            favoriteVo.setCount(favorite.getCount());
            favoriteVo.setAvatar(favorite.getAvatar());
            favoriteVoList.add(favoriteVo);
        }

        return Result.success(favoriteVoList);
    }

    @Override
    public Result upload(FavoriteParam favoriteParam) {
        Long articleId = favoriteParam.getArticleId();
        Long favoriteId = favoriteParam.getFavorite();
        FavoriteArticle favoriteArticle = new FavoriteArticle();
        favoriteArticle.setArticleId(articleId);
        favoriteArticle.setFavoriteId(favoriteId);
        favoriteArticleMapper.insert(favoriteArticle);
        return Result.success(favoriteArticle);
    }

    @Override
    public Result detailById(Long id) {
        Favorite favorite = favoriteMapper.selectById(id);
        FavoriteVo favoriteVo = new FavoriteVo();

        favoriteVo.setId(id);
        favoriteVo.setFavoriteName(favorite.getName());
        return Result.success(favoriteVo);
    }

    @Override
    public Result showOthersFavoriteByUserId(Long id) {
        List<Favorite> favoriteList = favoriteMapper.selectListByUserId(id);

        ArrayList<FavoriteVo> favoriteVoList = new ArrayList<>();
        for(Favorite favorite : favoriteList){
            FavoriteVo favoriteVo = new FavoriteVo();
            favoriteVo.setId(favorite.getId());
            favoriteVo.setFavoriteName(favorite.getName());
            favoriteVo.setCount(favorite.getCount());
            favoriteVo.setAvatar(favorite.getAvatar());
            favoriteVoList.add(favoriteVo);
        }

        return Result.success(favoriteVoList);
    }
}
