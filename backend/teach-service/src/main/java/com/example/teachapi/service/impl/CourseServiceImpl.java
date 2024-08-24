package com.example.teachapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.dto.AdminCourseInfoVo;
import com.example.teachapi.dao.dto.SysUser;
import com.example.teachapi.dao.mapper.CourseMapper;
import com.example.teachapi.dao.pojo.Course;
import com.example.teachapi.service.CourseService;
import com.example.teachapi.vo.ErrorCode;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * @author ddddm
 **/
@CrossOrigin
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public Course selectById(Long id) {
        return courseMapper.selectById(id);
    }

    @Override
    public Page<AdminCourseInfoVo> selectJoinPage(AdminPageParam adminPageParam, MPJQueryWrapper<Course> queryWrapper){
        return courseMapper.selectJoinPage(new Page<>(adminPageParam.getPage(), adminPageParam.getPageSize()), AdminCourseInfoVo.class, queryWrapper);
    }

    @Override
    public Course selectCertainOne(String certain, List<Long> ids) {
        return courseMapper.selectOne(new QueryWrapper<Course>()
                .select(certain)
                .in("id", ids)
        );
    }

    @Override
    public void insert(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void updateById(Course course) {
        courseMapper.updateById(course);
    }

    @Override
    public Result deleteById(Long id) {
        try {
            courseMapper.deleteById(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public List<Course> selectBatchIds(List<Long> ids) {
        return courseMapper.selectBatchIds(ids);
    }

    @Override
    public Result deleteBatchIds(List<Long> ids) {
        try {
            courseMapper.deleteBatchIds(ids);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public void update(Course course, LambdaUpdateWrapper<Course> queryWrapper) {
        courseMapper.update(course, queryWrapper);
    }
}
