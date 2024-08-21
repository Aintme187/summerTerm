package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String avatar;
    private String categoryName;
    private String description;
}
