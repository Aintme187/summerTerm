package com.example.manageapi.dao.dto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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