package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Keith
 */
@Data
public class ArticleMaterial {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private LocalDateTime time;
    private Long articleId;
    private String url;
    private String name;
    private String size;
}
