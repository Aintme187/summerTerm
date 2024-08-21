package com.example.manageapi.service;

import com.example.manageapi.dao.pojo.Favorite;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.FavoriteParam;

public interface FavoriteService {
    Result list();

    Result listById(Long id);

    Result create(Favorite favorite);

    Result showFavorite();

    Result upload(FavoriteParam favoriteParam);

    Result detailById(Long id);

    Result showOthersFavoriteByUserId(Long id);
}
