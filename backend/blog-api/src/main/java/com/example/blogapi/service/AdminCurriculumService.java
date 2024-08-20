package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Curriculum;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.AdminPageParam;
import com.example.blogapi.vo.params.BatchUpdateCurriculumsParam;

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
