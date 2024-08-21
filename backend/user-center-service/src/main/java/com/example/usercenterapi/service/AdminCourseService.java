package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.Course;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.BatchUpdateCoursesParam;

import java.util.List;

public interface AdminCourseService {
    Result listCoursePage(AdminPageParam adminPageParam);

    Result getCourseInfoById(Long id);

    Result addCourse(Course course);

    Result updateCourse(Course course);

    Result deleteCourse(Long id);

    Result batchDeleteCourses(List<Long> ids);

    Result batchUpdateCourses(BatchUpdateCoursesParam batchUpdateCoursesParam);
}
