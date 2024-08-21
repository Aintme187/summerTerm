package com.example.blogapi.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class CommentVo  {
    private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> childrens;
    private String createDate;
    private Integer level;
    private UserVo toUser;
}