package com.example.blogapi.vo;

import com.example.blogapi.dao.pojo.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class AdminCurriculumVo {
    private List<Curriculum> curriculumList;

    private Long curriculumCount;
}
