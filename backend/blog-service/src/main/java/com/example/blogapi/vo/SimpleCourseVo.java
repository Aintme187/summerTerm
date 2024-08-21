package com.example.blogapi.vo;

import lombok.Data;

import java.beans.PropertyVetoException;

/**
 * @author Keith
 */
@Data
public class SimpleCourseVo {

    private String courseId;

    private String courseName;

    private String teacherName;
}
