package com.example.usercenterapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.usercenterapi.dao.pojo.Curriculum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 15252
 */
@Mapper
public interface CurriculumMapper extends BaseMapper<Curriculum> {

    @Select("SELECT * FROM curriculum WHERE id = #{curriculumId}")
    Curriculum selectCurriculumById(Long curriculumId);
}
