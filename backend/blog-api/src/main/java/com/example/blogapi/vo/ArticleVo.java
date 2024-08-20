package com.example.blogapi.vo;


import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.dao.pojo.Tag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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
