package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

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
