package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Favorite;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.FavoriteParam;

public interface FavoriteService {
    Result list();

    Result listById(Long id);

    Result create(Favorite favorite);

    Result showFavorite();

    Result upload(FavoriteParam favoriteParam);

    Result detailById(Long id);

    Result showOthersFavoriteByUserId(Long id);
}
