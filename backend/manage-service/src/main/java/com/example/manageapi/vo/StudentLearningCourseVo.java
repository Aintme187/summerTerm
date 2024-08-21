package com.example.manageapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentLearningCourseVo {

    private List<StudentLearningCourseInfoVo> studentLearningCourseInfoVoList;

    private Long studentLearningCourseInfoVoCount;
}
