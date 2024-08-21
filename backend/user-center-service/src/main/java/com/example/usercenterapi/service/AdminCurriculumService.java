package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.Curriculum;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.BatchUpdateCurriculumsParam;

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
