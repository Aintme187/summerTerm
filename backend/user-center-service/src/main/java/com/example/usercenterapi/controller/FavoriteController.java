package com.example.usercenterapi.controller;

import com.example.usercenterapi.dao.pojo.Favorite;
import com.example.usercenterapi.service.FavoriteService;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.FavoriteParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    /**
     * 根据收藏夹id展示所有关联文章
     * @param id
     * @return
     */

    @PostMapping("list/{id}")
    public Result listById(@PathVariable("id") Long id){
        return favoriteService.listById(id);
    }

    /**
     * 根据收藏夹id找出收藏夹信息，在上方显示，返回一个favoriteVo
     * @param id
     * @return
     */
    @PostMapping("detail/{id}")
    public Result detailById(@PathVariable Long id){
        return favoriteService.detailById(id);
    }
    /**
     * 用户创建收藏夹
     * @param favorite
     * @return
     */
    @PostMapping("create")
    public Result create(@RequestBody Favorite favorite){
        return favoriteService.create(favorite);
    }

    /**
     * 《在上传文章到收藏夹的时候》，展示用户的收藏夹，brief
     * @param
     * @return
     */
    @PostMapping("showFavorite")
    public Result showFavoriteByUserId(){
        return favoriteService.showFavorite();
    }

    @PostMapping("showFavorite/{id}")
    public Result showOthersFavoriteByUserId(@PathVariable("id") Long id){
        return favoriteService.showOthersFavoriteByUserId(id);
    }
    /**
     * 用户将指定文章添加进收藏夹中
     */
    @PostMapping("upload")
    public Result upload(@RequestBody FavoriteParam favoriteParam){
        return favoriteService.upload(favoriteParam);
    }


}
