package com.example.manageapi.service;

import com.example.manageapi.dao.pojo.Course;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateCoursesParam;

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
