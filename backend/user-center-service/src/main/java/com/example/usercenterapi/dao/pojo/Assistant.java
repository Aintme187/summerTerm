package com.example.usercenterapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Keith
 */
@Data
public class Assistant {
    @TableId
    private Long id;

    private Long studentId;

    private Long courseId;

    private int status;
}
