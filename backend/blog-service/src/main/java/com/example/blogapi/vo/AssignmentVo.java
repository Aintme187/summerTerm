package com.example.blogapi.vo;

import com.example.blogapi.dao.pojo.Submission;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Keith
 */
@Data
public class AssignmentVo {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private String url;

    private Long courseId;

    private String fileName;

    // 当前学生的提交信息
    private Submission submission;
}
