package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.Favorite;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.FavoriteParam;

public interface FavoriteService {
    Result list();

    Result listById(Long id);

    Result create(Favorite favorite);

    Result showFavorite();

    Result upload(FavoriteParam favoriteParam);

    Result detailById(Long id);

    Result showOthersFavoriteByUserId(Long id);
}
