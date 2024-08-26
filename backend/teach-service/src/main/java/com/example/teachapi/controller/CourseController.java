package com.example.teachapi.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.dto.AdminCourseInfoVo;
import com.example.teachapi.dao.pojo.Course;
import com.example.teachapi.service.CourseService;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.example.teachapi.vo.params.BatchUpdateCoursesParam;
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

    @PostMapping("/selectJoinPage")
    Result selectJoinPage(@RequestBody AdminPageParam adminPageParam) {
        return courseService.selectJoinPage(adminPageParam);
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Course course) {
        courseService.insert(course);
    }

    @PutMapping("/updateById")
    public void updateById(@RequestBody Course course) {
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
    Result update(@RequestBody BatchUpdateCoursesParam batchUpdateCoursesParam) {
        return courseService.update(batchUpdateCoursesParam);
    }
}
