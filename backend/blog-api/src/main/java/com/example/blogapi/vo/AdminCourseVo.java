package com.example.blogapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class AdminCourseVo {
    private List<AdminCourseInfoVo> courseVoList;

    private Long courseVoCount;
}
