package com.example.teachapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.pojo.Curriculum;
import com.example.teachapi.service.CurriculumService;
import com.example.teachapi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ddddm
 **/
@RestController
@RequestMapping("curriculum")
@CrossOrigin
public class CurriculumController {
    @Autowired
    private CurriculumService curriculumService;

    @GetMapping("/selectById")
    public Curriculum selectById(@RequestParam("id") Long id){
        return curriculumService.selectById(id);
    }

    @GetMapping("/selectPage")
    public Page<Curriculum> selectPage(@RequestParam Page page, QueryWrapper queryWrapper) {
        return curriculumService.selectPage(page, queryWrapper);
    }

    @PostMapping("/insert")
    public void insert(@RequestParam Curriculum curriculum) {
        curriculumService.insert(curriculum);
    }

    @PutMapping("/updateById")
    public void updateById(@RequestParam Curriculum curriculum) {
        curriculumService.updateById(curriculum);
    }

    @DeleteMapping("/deleteById")
    Result deleteById(@RequestParam Long id) {
        return curriculumService.deleteById(id);
    }

    @GetMapping("/selectBatchIds")
    List<Curriculum> selectBatchIds(@RequestParam List<Long> ids) {
        return curriculumService.selectBatchIds(ids);
    }

    @DeleteMapping("/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids) {
        return curriculumService.deleteBatchIds(ids);
    }

    @PutMapping("/update")
    void update(@RequestParam Curriculum curriculum, LambdaUpdateWrapper<Curriculum> queryWrapper) {
        curriculumService.update(curriculum, queryWrapper);
    }
}
