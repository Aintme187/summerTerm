package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String content;
    private Date createDate;
    private Long articleId;
    private Long authorId;
    private Long parentId;
    private Long toUid;
    private Integer level;
}