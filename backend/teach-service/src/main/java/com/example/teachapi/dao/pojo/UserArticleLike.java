package com.example.teachapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserArticleLike {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long articleId;
}
