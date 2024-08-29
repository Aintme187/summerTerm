package com.example.teachapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachapi.dao.pojo.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 15252
 */
@Mapper
public interface MaterialMapper extends BaseMapper<Material> {
    @Select("SELECT * FROM material WHERE course_id = #{courseId}")
    List<Material> selectMaterialsByCourseId(@Param("courseId") Long courseId);

    @Select("SELECT id, course_id, url, name, size, time FROM material WHERE course_id = #{courseId}")
    List<Material> findMaterialsByCourseId(Long courseId);


}
