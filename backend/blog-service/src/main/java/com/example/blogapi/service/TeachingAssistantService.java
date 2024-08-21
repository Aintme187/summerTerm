package com.example.blogapi.service;

import com.example.blogapi.vo.CourseVo;

import java.util.List;

public interface TeachingAssistantService {
    // 查看所任助教的课程
    List<CourseVo> getAssistCourses(Long assistantId);
}
