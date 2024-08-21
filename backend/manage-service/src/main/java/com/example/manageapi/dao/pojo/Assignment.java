package com.example.manageapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Keith
 */
@Data
public class Assignment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    // 老师发布的作业资料
    private String url;

    private Long courseId;

    private String fileName;
}
