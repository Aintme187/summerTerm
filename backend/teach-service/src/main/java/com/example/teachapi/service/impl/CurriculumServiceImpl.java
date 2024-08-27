package com.example.teachapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.mapper.CurriculumMapper;
import com.example.teachapi.dao.pojo.Curriculum;
import com.example.teachapi.service.CurriculumService;
import com.example.teachapi.vo.AdminCurriculumVo;
import com.example.teachapi.vo.ErrorCode;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.example.teachapi.vo.params.BatchUpdateCurriculumsParam;
import com.example.teachapi.vo.params.FilterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * @author ddddm
 **/
@CrossOrigin
@Service
public class CurriculumServiceImpl implements CurriculumService {
    @Autowired
    private CurriculumMapper curriculumMapper;
    @Override
    public Curriculum selectById(Long id) {
        return curriculumMapper.selectById(id);
    }

    @Override
    public Result selectPage(AdminPageParam adminPageParam){
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
    public void insert(Curriculum curriculum) {
        curriculumMapper.insert(curriculum);
    }

    @Override
    public void updateById(Curriculum curriculum) {
        curriculumMapper.updateById(curriculum);
    }

    @Override
    public Result deleteById(Long id) {
        try {
            curriculumMapper.deleteById(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public List<Curriculum> selectBatchIds(List<Long> ids) {
        return curriculumMapper.selectBatchIds(ids);
    }

    @Override
    public Result deleteBatchIds(List<Long> ids) {
        try {
            curriculumMapper.deleteBatchIds(ids);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public Result update(BatchUpdateCurriculumsParam batchUpdateCurriculumsParam) {

        List<Long> ids = batchUpdateCurriculumsParam.getIds();
        Curriculum curriculum = batchUpdateCurriculumsParam.getCurriculum();
        ErrorCode errorCode = errorInCurriculum(curriculum);
        if (errorCode != null) return Result.fail(errorCode);
        curriculum.setId(null);
        curriculumMapper.update(curriculum, new LambdaUpdateWrapper<Curriculum>().in(Curriculum::getId, ids));
        return Result.success(null);
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
