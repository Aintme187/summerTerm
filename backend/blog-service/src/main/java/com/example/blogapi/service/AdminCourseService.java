package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Course;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.AdminPageParam;
import com.example.blogapi.vo.params.BatchUpdateCoursesParam;

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
