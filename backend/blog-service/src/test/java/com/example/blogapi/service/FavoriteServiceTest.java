package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Favorite;
import com.example.blogapi.vo.CategoryVo;
import com.example.blogapi.vo.FavoriteVo;
import com.example.blogapi.vo.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FavoriteServiceTest {
    @Autowired
    private FavoriteService favoriteService;
    @Test
    void list() {
        Result res = favoriteService.list();
        Assertions.assertNotEquals(res.getData(), 0);
    }

    @Test
    void listByIdP() {
        Result res = favoriteService.listById(1L);
        Assertions.assertNotNull((Favorite)res.getData());
    }
    @Test
    void listByIdN() {
        Result res = favoriteService.listById(0L);
        Assertions.assertNull((Favorite)res.getData());
    }


    @Test
    void showFavoriteP() {
        Result result = favoriteService.showFavorite();
        ArrayList<FavoriteVo> favoriteVoList = (ArrayList<FavoriteVo>)result.getData();
        Assertions.assertNotEquals(favoriteVoList.size(), 0);
    }

    @Test
    void detailByIdP() {
        Result result = favoriteService.detailById(1L);
        Assertions.assertNotNull(result.getData());
    }
    @Test
    void detailByIdN() {
        assertThrows(NullPointerException.class, () -> {
            Result result = favoriteService.detailById(0L);
        });
    }


}