package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Keith
 */
@Data
public class Submission {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String submissionUrl;

    private LocalDateTime time;

    private boolean status;

    private Long score;

    private String feedbackUrl;

    private String comment;

    private Long assignmentId;

    private Long studentId;

    private String fileName;
}
