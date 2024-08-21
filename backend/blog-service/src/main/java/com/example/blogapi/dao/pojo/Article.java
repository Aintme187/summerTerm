package com.example.blogapi.dao.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.blogapi.vo.CategoryVo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Article {
    public static final int Article_TOP = 1;
    public static final int Article_Common = 0;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    private Long authorId;
    private Long bodyId;
    private Long categoryId;
    private Integer weight;
    private Date createDate;
    private Integer likeCounts;
    private Boolean isLike;
}