package com.example.blogapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogapi.dao.pojo.Sc;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 15252
 */
@Mapper
public interface SCMapper extends BaseMapper<Sc> {
    @Select("select * FROM sc WHERE student_id = #{studentId} AND course_id = #{courseId}")
    Sc selectByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Select("SELECT * FROM sc WHERE student_id = #{studentId} AND status = 1")
    List<Sc> selectJoinedCoursesByStudentId(Long studentId);

    @Select("select * FROM sc WHERE course_id = #{courseId} AND status = 0")
    List<Sc> selectApplicationByCourseId(Long courseId);
}
