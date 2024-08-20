package com.example.blogapi.controller;

import com.example.blogapi.dao.pojo.Tag;
import com.example.blogapi.service.TagService;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;
    @GetMapping("hot")
    public Result hot(){
        int limit = 6;
        List<TagVo> tagVoList = tagService.hot(limit);
        return Result.success(tagVoList);
    }

    @GetMapping
    public Result findAllTags(){
        return tagService.findAllTags();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }

    @PostMapping("create")
    public Result create(@RequestBody Tag tag){
        return tagService.create(tag);
    }
}
