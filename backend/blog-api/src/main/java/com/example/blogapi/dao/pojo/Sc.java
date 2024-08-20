package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Keith
 */
@Data
public class Sc {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long studentId;

    private Long courseId;

    private int score;

    private int status;
}
