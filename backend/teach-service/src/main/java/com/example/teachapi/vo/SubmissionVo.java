package com.example.teachapi.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ddddm
 **/
@Data
public class SubmissionVo {
    private Long id;

    private String submissionUrl;

    private LocalDateTime time;

    private boolean status;

    private Long assignmentId;

    private Long studentId;

    private String fileName;

    private String studentName;
}
