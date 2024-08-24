package com.example.manageapi.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manageapi.dao.dto.Course;
import com.example.manageapi.dao.dto.Curriculum;
import com.example.manageapi.vo.AdminCourseInfoVo;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "teach-service")
public interface teachClient {
    @GetMapping("/course/selectById")
    Course selectById(@RequestParam("id") Long id);

    @GetMapping("/course/selectJoinPage")
    Page<AdminCourseInfoVo> selectJoinPage(@RequestParam AdminPageParam adminPageParam, MPJQueryWrapper<Course> queryWrapper);

    @GetMapping("/course/selectCertainOne")
    Course selectCertainOne(@RequestParam String certain, List<Long> ids);

    @PostMapping("/course/insert")
    void insert(@RequestParam Course course);

    @PutMapping("/course/updateById")
    void updateById(@RequestParam Course course);

    @DeleteMapping("/course/deleteById")
    Result deleteById(@RequestParam Long id);

    @GetMapping("/course/selectBatchIds")
    List<Course> selectBatchIds(@RequestParam List<Long> ids);

    @DeleteMapping("/course/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids);

    @PutMapping("/course/update")
    void update(@RequestParam Course course, LambdaUpdateWrapper<Course> queryWrapper);

    @GetMapping("/curriculum/selectById")
    Curriculum selectCurriculumById(@RequestParam("id") Long id);

    @GetMapping("/curriculum/selectPage")
    Page<Curriculum> selectPage(@RequestParam AdminPageParam adminPageParam, QueryWrapper<Curriculum> queryWrapper);

    @PostMapping("/curriculum/insert")
    void insert(@RequestParam Curriculum curriculum);

    @PutMapping("/curriculum/updateById")
    void updateById(@RequestParam Curriculum curriculum);

    @DeleteMapping("/curriculum/deleteById")
    Result deleteCurriculumById(@RequestParam Long id);

    @GetMapping("/curriculum/selectBatchIds")
    List<Curriculum> selectCurriculumBatchIds(@RequestParam List<Long> ids);

    @DeleteMapping("/curriculum/DeleteBatchByIds")
    Result deleteCurriculumBatchIds(@RequestParam List<Long> ids);

    @PutMapping("/curriculum/update")
    void update(@RequestParam Curriculum curriculum, LambdaUpdateWrapper<Curriculum> queryWrapper);
}
