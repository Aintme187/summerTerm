package com.example.manageapi.dao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class Favorite {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Long count;
    private DateTime createTime;
    private String avatar;
}
