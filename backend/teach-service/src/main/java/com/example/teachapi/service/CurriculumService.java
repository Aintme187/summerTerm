package com.example.teachapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.pojo.Curriculum;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface CurriculumService {
    Curriculum selectById(Long id);

    Page<Curriculum> selectPage(AdminPageParam adminPageParam, QueryWrapper<Curriculum> queryWrapper);

    void insert(Curriculum curriculum);

    void updateById(Curriculum curriculum);

    Result deleteById(Long id);

    List<Curriculum> selectBatchIds(List<Long> ids);

    Result deleteBatchIds(List<Long> ids);

    void update(Curriculum curriculum, LambdaUpdateWrapper<Curriculum> queryWrapper);
}
