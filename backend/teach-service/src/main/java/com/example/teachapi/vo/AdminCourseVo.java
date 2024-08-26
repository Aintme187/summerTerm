package com.example.teachapi.vo;

import com.example.teachapi.dao.dto.AdminCourseInfoVo;
import lombok.Data;

import java.util.List;

@Data
public class AdminCourseVo {
    private List<AdminCourseInfoVo> courseVoList;

    private Long courseVoCount;
}
