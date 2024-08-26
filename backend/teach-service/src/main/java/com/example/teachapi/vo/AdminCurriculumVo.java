package com.example.teachapi.vo;

import com.example.teachapi.dao.pojo.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class AdminCurriculumVo {
    private List<Curriculum> curriculumList;

    private Long curriculumCount;
}
