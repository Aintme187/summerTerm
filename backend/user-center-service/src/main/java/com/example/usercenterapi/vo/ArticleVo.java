package com.example.usercenterapi.vo;


import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    private Integer weight;
    private String createDate;
    private String author;
    private SysUserVo authorInfo;
    private ArticleBodyVo body;
    private List<TagVo> tags;
    private CategoryVo category;
    private Integer likeCounts;

}
