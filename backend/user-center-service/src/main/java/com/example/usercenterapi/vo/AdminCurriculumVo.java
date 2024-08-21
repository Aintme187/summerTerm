package com.example.usercenterapi.vo;

import com.example.usercenterapi.dao.pojo.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class AdminCurriculumVo {
    private List<Curriculum> curriculumList;

    private Long curriculumCount;
}
