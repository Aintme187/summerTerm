package com.example.blogapi.vo;

import lombok.Data;

/**
 * @author ddddm
 **/
@Data
public class AssistantApplicationVo {
    private Long assistantId;
    private Long studentId;
    private Long courseId;
    private String studentName;
    private String courseName;
}
