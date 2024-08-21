package com.example.manageapi.service;

import com.example.manageapi.vo.CourseVo;

import java.util.List;

public interface TeachingAssistantService {
    // 查看所任助教的课程
    List<CourseVo> getAssistCourses(Long assistantId);
}
