package com.example.usercenterapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserFavorite {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long favoriteId;
}
