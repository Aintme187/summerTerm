package com.example.blogapi.controller;

import com.example.blogapi.common.aop.LogAnnotation;
import com.example.blogapi.dao.pojo.Curriculum;
import com.example.blogapi.service.AdminCurriculumService;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.AdminPageParam;
import com.example.blogapi.vo.params.BatchUpdateCurriculumsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/curriculum")
public class AdminCurriculumController {
    @Autowired
    private AdminCurriculumService adminCurriculumService;

    @PostMapping("list")
    @LogAnnotation(module = "管理课程", operator = "获取课程列表")
    public Result curriculums(@RequestBody AdminPageParam adminPageParam) {
        return adminCurriculumService.listCurriculumPage(adminPageParam);
    }

    @GetMapping("info/{id}")
    public Result getCurriculumInfoById(@PathVariable("id") Long curriculumId) {
        return adminCurriculumService.getCurriculumInfoById(curriculumId);
    }

    @PostMapping("add")
    public Result addCurriculum(@RequestBody Curriculum curriculum) {
        return adminCurriculumService.addCurriculum(curriculum);
    }

    @PostMapping("update")
    public Result updateCurriculum(@RequestBody Curriculum curriculum) {
        return adminCurriculumService.updateCurriculum(curriculum);
    }

    @PostMapping("delete/{id}")
    public Result deleteCurriculum(@PathVariable("id") Long curriculumId) {
        return adminCurriculumService.deleteCurriculum(curriculumId);
    }

    @PostMapping("batch/delete")
    public Result batchDeleteCurriculums(@RequestBody List<Long> curriculumIds) {
        return adminCurriculumService.batchDeleteCurriculums(curriculumIds);
    }

    @PostMapping("batch/update")
    public Result batchUpdateCurriculums(@RequestBody BatchUpdateCurriculumsParam batchUpdateCurriculumsParam) {
        return adminCurriculumService.batchUpdateCurriculums(batchUpdateCurriculumsParam);
    }
}
