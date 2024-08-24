package com.example.teachapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.mapper.CurriculumMapper;
import com.example.teachapi.dao.pojo.Curriculum;
import com.example.teachapi.service.CurriculumService;
import com.example.teachapi.vo.ErrorCode;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
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
    public Page<Curriculum> selectPage(AdminPageParam adminPageParam, QueryWrapper<Curriculum> queryWrapper){
        return curriculumMapper.selectPage(new Page<>(adminPageParam.getPage(), adminPageParam.getPageSize()), queryWrapper);
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
    public void update(Curriculum curriculum, LambdaUpdateWrapper<Curriculum> queryWrapper) {
        curriculumMapper.update(curriculum, queryWrapper);
    }
}
