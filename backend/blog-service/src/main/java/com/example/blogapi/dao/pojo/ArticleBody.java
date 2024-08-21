package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ArticleBody {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
