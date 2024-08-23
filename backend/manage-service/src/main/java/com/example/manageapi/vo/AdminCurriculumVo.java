package com.example.manageapi.vo;

import com.example.manageapi.dao.dto.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class AdminCurriculumVo {
    private List<Curriculum> curriculumList;

    private Long curriculumCount;
}
