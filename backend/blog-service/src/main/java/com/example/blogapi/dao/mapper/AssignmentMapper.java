package com.example.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogapi.dao.pojo.Assignment;
import com.example.blogapi.vo.AssignmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 15252
 */
@Mapper
public interface AssignmentMapper extends BaseMapper<Assignment> {

    @Select("SELECT * FROM assignment WHERE course_id = #{courseId}")
    List<AssignmentVo> getAssignmentsByCourseId(@Param("courseId") Long courseId);
}
