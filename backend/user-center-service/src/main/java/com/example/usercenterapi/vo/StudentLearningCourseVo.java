package com.example.usercenterapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentLearningCourseVo {

    private List<StudentLearningCourseInfoVo> studentLearningCourseInfoVoList;

    private Long studentLearningCourseInfoVoCount;
}
