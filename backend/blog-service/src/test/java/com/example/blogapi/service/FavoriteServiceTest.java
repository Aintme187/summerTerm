package com.example.blogapi.service;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class FavoriteServiceTest {

    @Test
    public void list() {
    }

    @Test
    public void listById() {
    }

    @Test
    public void create() {
    }

    @Test
    public void showFavorite() {
    }

    @Test
    public void upload() {
    }

    @Test
    public void detailById() {
    }

    @Test
    public void showOthersFavoriteByUserId() {
    }
}