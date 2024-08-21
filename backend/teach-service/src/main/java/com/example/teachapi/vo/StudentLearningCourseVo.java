package com.example.teachapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentLearningCourseVo {

    private List<StudentLearningCourseInfoVo> studentLearningCourseInfoVoList;

    private Long studentLearningCourseInfoVoCount;
}
