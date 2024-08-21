package com.example.manageapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
