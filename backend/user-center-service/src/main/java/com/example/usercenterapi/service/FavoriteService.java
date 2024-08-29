package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.Favorite;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.FavoriteParam;

public interface FavoriteService {
    Result list();

    Result create(Favorite favorite);
}
