package com.example.usercenterapi.service;

import com.example.usercenterapi.vo.CourseVo;

import java.util.List;

public interface TeachingAssistantService {
    // 查看所任助教的课程
    List<CourseVo> getAssistCourses(Long assistantId);
}
