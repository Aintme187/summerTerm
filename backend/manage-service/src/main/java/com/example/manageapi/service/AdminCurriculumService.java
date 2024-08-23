package com.example.manageapi.service;

import com.example.manageapi.dao.dto.Curriculum;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateCurriculumsParam;

import java.util.List;

public interface AdminCurriculumService {
    Result listCurriculumPage(AdminPageParam adminPageParam);

    Result getCurriculumInfoById(Long id);

    Result addCurriculum(Curriculum curriculum);

    Result updateCurriculum(Curriculum curriculum);

    Result deleteCurriculum(Long id);

    Result batchDeleteCurriculums(List<Long> ids);

    Result batchUpdateCurriculums(BatchUpdateCurriculumsParam batchUpdateCurriculumsParam);
}
