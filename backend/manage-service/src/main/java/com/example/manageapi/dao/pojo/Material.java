package com.example.manageapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Keith
 */
@Data
public class Material {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime time;

    private Long courseId;

    private String url;

    private String name;

    private String size;
}
