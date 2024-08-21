package com.example.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenterapi.dao.mapper.CurriculumMapper;
import com.example.usercenterapi.dao.pojo.Curriculum;
import com.example.usercenterapi.service.AdminCurriculumService;
import com.example.usercenterapi.vo.AdminCurriculumVo;
import com.example.usercenterapi.vo.ErrorCode;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.BatchUpdateCurriculumsParam;
import com.example.usercenterapi.vo.params.FilterData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCurriculumServiceImpl implements AdminCurriculumService {

    @Autowired
    private CurriculumMapper curriculumMapper;

    @Override
    public Result listCurriculumPage(AdminPageParam adminPageParam) {
        QueryWrapper<Curriculum> queryWrapper = new QueryWrapper<>();
        List<FilterData> filterDataList = adminPageParam.getFilterDataList();
        if (FilterData.injectFilter(queryWrapper, filterDataList)) {
            AdminCurriculumVo adminCurriculumVo = new AdminCurriculumVo();
            Page<Curriculum> page = curriculumMapper.selectPage(new Page<>(adminPageParam.getPage(), adminPageParam.getPageSize()), queryWrapper);
            adminCurriculumVo.setCurriculumList(page.getRecords());
            adminCurriculumVo.setCurriculumCount(page.getTotal());
            return Result.success(adminCurriculumVo);
        } else {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public Result getCurriculumInfoById(Long id) {
        Curriculum curriculum = curriculumMapper.selectById(id);
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
        curriculumMapper.insert(curriculum);
        return Result.success(null);
    }

    @Override
    public Result updateCurriculum(Curriculum curriculum) {
        if (curriculumMapper.selectById(curriculum.getId()) == null) return Result.fail(ErrorCode.NO_CURRICULUM);
        ErrorCode errorCode = errorInCurriculum(curriculum);
        if (errorCode != null) return Result.fail(errorCode);
        curriculumMapper.updateById(curriculum);
        return Result.success(null);
    }

    @Override
    public Result deleteCurriculum(Long id) {
        if (curriculumMapper.selectById(id) == null) return Result.fail(ErrorCode.NO_CURRICULUM);
        curriculumMapper.deleteById(id);
        return Result.success(null);
    }

    @Override
    public Result batchDeleteCurriculums(List<Long> ids) {
        if (curriculumMapper.selectBatchIds(ids).size() != ids.size()) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            curriculumMapper.deleteBatchIds(ids);
            return Result.success(null);
        }
    }

    @Override
    public Result batchUpdateCurriculums(BatchUpdateCurriculumsParam batchUpdateCurriculumsParam) {
        List<Long> ids = batchUpdateCurriculumsParam.getIds();
        Curriculum curriculum = batchUpdateCurriculumsParam.getCurriculum();
        ErrorCode errorCode = errorInCurriculum(curriculum);
        if (errorCode != null) return Result.fail(errorCode);
        curriculum.setId(null);
        curriculumMapper.update(curriculum, new LambdaQueryWrapper<Curriculum>().in(Curriculum::getId, ids));
        return Result.success(null);
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
