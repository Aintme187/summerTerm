package com.example.teachapi.vo;

import lombok.Data;

/**
 * @author ddddm
 **/
@Data
public class ScApplicationVo {
    private Long scId;
    private Long studentId;
    private Long courseId;
    private String studentName;
    private String courseName;
}
