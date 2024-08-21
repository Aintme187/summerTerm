package com.example.teachapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachapi.dao.pojo.Assistant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 15252
 */
@Mapper
public interface AssistantMapper extends BaseMapper<Assistant> {
    @Delete("DELETE FROM assistant WHERE student_id = #{studentId} AND course_id = #{courseId}")
    int deleteByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Select("select * FROM assistant WHERE student_id = #{studentId} AND course_id = #{courseId}")
    Assistant selectByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Select("SELECT * FROM assistant WHERE student_id = #{studentId}")
    List<Assistant> selectByStudentId(@Param("studentId") Long studentId);

    @Select("SELECT * FROM assistant WHERE course_id = #{courseId} AND status = 0")
    List<Assistant> selectApplicationByCourseId(@Param("courseId") Long courseId);
}
