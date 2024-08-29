package com.example.teachapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachapi.dao.pojo.Submission;
import com.example.teachapi.vo.SubmissionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 15252
 */
@Mapper
public interface SubmissionMapper extends BaseMapper<Submission> {

    @Select("SELECT * FROM submission WHERE assignment_id = #{assignmentId} AND student_id = #{studentId}")
    Submission getSubmissionByAssignmentAndStudent(@Param("assignmentId") Long assignmentId, @Param("studentId") Long studentId);

    @Select("SELECT su.id, su.submission_Url, su.time, su.status, su.assignment_id AS assignmentId, su.student_id AS studentId, su.file_name AS fileName, su.student_name AS studentName " +
            "FROM submission su " +
            "WHERE su.assignment_id = #{assignmentId} AND su.status = false")
    List<SubmissionVo> selectByAssignmentId(Long assignmentId);
}
