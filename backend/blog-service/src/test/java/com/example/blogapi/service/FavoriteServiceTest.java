package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Favorite;
import com.example.blogapi.vo.Result;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class FavoriteServiceTest {
    @Autowired
    private FavoriteService favoriteService;
    @Test
    public void list() {
        Result res = favoriteService.list();
        Assertions.assertNotEquals(res.getData(), 0);
    }

    @Test
    public void listByIdP() {
        Result res = favoriteService.listById(1L);
        Assertions.assertNotNull((Favorite)res.getData());
    }
    @Test
    public void listByIdN() {
        Result res = favoriteService.listById(0L);
        Assertions.assertNull((Favorite)res.getData());
    }


//    @Test
//    public void showFavoriteP() {
//        Result result = favoriteService.showFavorite();
//        ArrayList<FavoriteVo> favoriteVoList = (ArrayList<FavoriteVo>)result.getData();
//        Assertions.assertNotEquals(favoriteVoList.size(), 0);
//    }

    @Test
    public void detailByIdP() {
        Result result = favoriteService.detailById(1L);
        Assertions.assertNotNull(result.getData());
    }
    @Test
    public void detailByIdN() {
        assertThrows(NullPointerException.class, () -> {
            Result result = favoriteService.detailById(0L);
        });
    }


}