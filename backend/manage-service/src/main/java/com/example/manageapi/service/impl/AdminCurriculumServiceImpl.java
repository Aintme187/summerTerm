package com.example.manageapi.service.impl;

import com.example.manageapi.client.teachClient;
import com.example.manageapi.dao.dto.Curriculum;
import com.example.manageapi.service.AdminCurriculumService;
import com.example.manageapi.vo.ErrorCode;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateCurriculumsParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCurriculumServiceImpl implements AdminCurriculumService {
    @Autowired
    private teachClient teachClient;

    @Override
    public Result listCurriculumPage(AdminPageParam adminPageParam) {
        return teachClient.selectPage(adminPageParam);
    }

    @Override
    public Result getCurriculumInfoById(Long id) {
        Curriculum curriculum = teachClient.selectCurriculumById(id);
        if (curriculum == null) return Result.fail(ErrorCode.NO_CURRICULUM);
        return Result.success(curriculum);
    }

    @Override
    public Result addCurriculum(Curriculum curriculum) {
        curriculum = noNull(curriculum);
        ErrorCode errorCode = errorInCurriculum(curriculum);
        if (errorCode != null) return Result.fail(errorCode);
        if (curriculum.getIntroduction() == null) curriculum.setIntroduction("");
        curriculum.setId(null);
        teachClient.insert(curriculum);
        return Result.success(null);
    }

    @Override
    public Result updateCurriculum(Curriculum curriculum) {
        if (teachClient.selectCurriculumById(curriculum.getId()) == null) return Result.fail(ErrorCode.NO_CURRICULUM);
        ErrorCode errorCode = errorInCurriculum(curriculum);
        if (errorCode != null) return Result.fail(errorCode);
        teachClient.updateById(curriculum);
        return Result.success(null);
    }

    @Override
    public Result deleteCurriculum(Long id) {
        if (teachClient.selectCurriculumById(id) == null) return Result.fail(ErrorCode.NO_CURRICULUM);
        return teachClient.deleteCurriculumById(id);
    }

    @Override
    public Result batchDeleteCurriculums(List<Long> ids) {
        if (teachClient.selectCurriculumBatchIds(ids).size() != ids.size()) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            return teachClient.deleteCurriculumBatchIds(ids);
        }
    }

    @Override
    public Result batchUpdateCurriculums(BatchUpdateCurriculumsParam batchUpdateCurriculumsParam) {
        return teachClient.update(batchUpdateCurriculumsParam);
    }

    private Curriculum noNull(Curriculum curriculum) {
        Curriculum newCurriculum = new Curriculum(null, "", 0., "");
        BeanUtils.copyProperties(curriculum, newCurriculum);
        return newCurriculum;
    }

    private ErrorCode errorInCurriculum(Curriculum curriculum) {
        if (curriculum.getName() != null && curriculum.getName().length() > 30)
            return ErrorCode.ILLEGAL_CURRICULUM_NAME;
        if (curriculum.getCredit() != null && curriculum.getCredit() <= 0.) return ErrorCode.ILLEGAL_CREDIT;
        if (curriculum.getIntroduction() != null && curriculum.getIntroduction().length() > 200)
            return ErrorCode.ILLEGAL_INTRODUCTION;
        return null;
    }
}
