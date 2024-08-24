package com.example.teachapi.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.dto.AdminCourseInfoVo;
import com.example.teachapi.dao.pojo.Course;
import com.example.teachapi.service.CourseService;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ddddm
 **/
@RestController
@RequestMapping("course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/selectById")
    public Course selectById(@RequestParam("id") Long id){
        return courseService.selectById(id);
    }

    @GetMapping("/selectJoinPage")
    Page<AdminCourseInfoVo> selectJoinPage(@RequestParam AdminPageParam adminPageParam, MPJQueryWrapper<Course> queryWrapper) {
        return courseService.selectJoinPage(adminPageParam, queryWrapper);
    }

    @GetMapping("/selectCertainOne")
    Course selectCertainOne(@RequestParam String certain, List<Long> ids) {
        return courseService.selectCertainOne(certain, ids);
    }

    @PostMapping("/insert")
    public void insert(@RequestParam Course course) {
        courseService.insert(course);
    }

    @PutMapping("/updateById")
    public void updateById(@RequestParam Course course) {
        courseService.updateById(course);
    }

    @DeleteMapping("/deleteById")
    Result deleteById(@RequestParam Long id) {
        return courseService.deleteById(id);
    }

    @GetMapping("/selectBatchIds")
    List<Course> selectBatchIds(@RequestParam List<Long> ids) {
        return courseService.selectBatchIds(ids);
    }

    @DeleteMapping("/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids) {
        return courseService.deleteBatchIds(ids);
    }

    @PutMapping("/update")
    void update(@RequestParam Course course, LambdaUpdateWrapper<Course> queryWrapper) {
        courseService.update(course, queryWrapper);
    }
}
