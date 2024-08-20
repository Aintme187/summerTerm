package com.example.blogapi.controller;

import com.example.blogapi.common.aop.LogAnnotation;
import com.example.blogapi.dao.pojo.Course;
import com.example.blogapi.service.AdminCourseService;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.AdminPageParam;
import com.example.blogapi.vo.params.BatchUpdateCoursesParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/course")
public class AdminCourseController {
    @Autowired
    private AdminCourseService adminCourseService;

    @PostMapping("list")
    @LogAnnotation(module = "管理教学班", operator = "获取教学班列表")
    public Result courses(@RequestBody AdminPageParam adminPageParam) {
        return adminCourseService.listCoursePage(adminPageParam);
    }

    @GetMapping("info/{id}")
    public Result getCourseInfoById(@PathVariable("id") Long courseId) {
        return adminCourseService.getCourseInfoById(courseId);
    }

    @PostMapping("add")
    public Result addCourse(@RequestBody Course course) {
        return adminCourseService.addCourse(course);
    }

    @PostMapping("update")
    public Result updateCourse(@RequestBody Course course) {
        return adminCourseService.updateCourse(course);
    }

    @PostMapping("delete/{id}")
    public Result deleteCourse(@PathVariable("id") Long courseId) {
        return adminCourseService.deleteCourse(courseId);
    }

    @PostMapping("batch/delete")
    public Result batchDeleteCourses(@RequestBody List<Long> courseIds) {
        return adminCourseService.batchDeleteCourses(courseIds);
    }

    @PostMapping("batch/update")
    public Result batchUpdateCourses(@RequestBody BatchUpdateCoursesParam batchUpdateCoursesParam) {
        return adminCourseService.batchUpdateCourses(batchUpdateCoursesParam);
    }
}
