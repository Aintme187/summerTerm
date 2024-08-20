package com.example.blogapi.vo;

import lombok.Data;

/**
 * @author Keith
 */
@Data
public class CourseVo {
    private Long courseId;

    private String courseName;

    private Long enrollment;

    private Long capacity;

    private String teacherName;

    private String deptName;

    private Integer credit;

    private String introduction;

    private Integer weekBegin;

    private Integer weekEnd;

    private Integer day;

    private Integer sectionBegin;

    private Integer sectionEnd;

    private String room;

    private Long categoryId;
}
