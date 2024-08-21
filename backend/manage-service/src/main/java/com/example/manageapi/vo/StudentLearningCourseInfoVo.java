package com.example.manageapi.vo;

import lombok.Data;

@Data
public class StudentLearningCourseInfoVo {
    private Long id;

    private String curriculumName;

    private Double credit;

    private String introduction;

    private Long enrollment;

    private Long capacity;

    private String teacherName;

    private String deptName;

    private Long weekBegin;

    private Long weekEnd;

    private Long day;

    private Long sectionBegin;

    private Long sectionEnd;

    private String room;
}
