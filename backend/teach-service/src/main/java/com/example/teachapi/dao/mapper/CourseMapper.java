package com.example.teachapi.dao.mapper;

import com.example.teachapi.dao.pojo.Course;
import com.example.teachapi.vo.CourseVo;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 15252
 */
@Mapper
public interface CourseMapper extends MPJBaseMapper<Course> {
    @Select("SELECT * FROM course WHERE id = #{courseId}")
    Course selectCourseByCourseId(@Param("courseId") Long courseId);

    @Select("SELECT CONCAT(s.week_begin, '-', s.week_end, '周', s.section_begin, '-', s.section_end, '节', s.room) AS timeAndRoom FROM Schedule s WHERE s.course_id = #{courseId}")
    List<String> getTimeAndRoom(@Param("courseId") Long courseId);

    @Select("SELECT c.id AS courseId, cu.name AS courseName, c.teacher_id AS teacherId, u.nickname AS teacherName, " +
            "c.enrollment, c.capacity, c.dept_name AS deptName, cu.credit, cu.introduction " +
            "FROM Course c " +
            "LEFT JOIN Curriculum cu ON c.curriculum_id = cu.id " +
            "LEFT JOIN sys_user u ON c.teacher_id = u.id " +
            "WHERE c.id NOT IN (SELECT course_id FROM SC WHERE student_id = #{studentId})")
    List<CourseVo> getAvailableCoursesByStudentId(@Param("studentId") Long studentId);

    @Select("SELECT c.id AS courseId, cu.name AS courseName, c.teacher_id AS teacherId, u.nickname AS teacherName, " +
            "c.enrollment, c.capacity, c.dept_name AS deptName, cu.credit, cu.introduction " +
            "FROM Course c " +
            "LEFT JOIN Curriculum cu ON c.curriculum_id = cu.id " +
            "LEFT JOIN sys_user u ON c.teacher_id = u.id " +
            "WHERE c.id NOT IN (SELECT course_id FROM SC WHERE student_id = #{studentId})" +
            "AND cu.name = #{courseName}")
    List<CourseVo> getAvailableCoursesByStudentIdAndCourseName(@Param("studentId") Long studentId, @Param("courseName") String courseName);

    @Select("SELECT c.id AS courseId, cu.name AS courseName, c.enrollment, c.capacity, u.nickname AS teacherName, " +
            "c.dept_name AS deptName, cu.credit, cu.introduction, " +
            "c.week_begin, c.week_end, c.day, c.section_begin, c.section_end, c.room " +
            "FROM SC sc " +
            "LEFT JOIN Course c ON sc.course_id = c.id " +
            "LEFT JOIN Curriculum cu ON c.curriculum_id = cu.id " +
            "LEFT JOIN sys_user u ON c.teacher_id = u.id " +
            "WHERE sc.student_id = #{studentId} " +
            "GROUP BY c.id")
    List<CourseVo> getSelectedCoursesByStudentId(@Param("studentId") Long studentId);

    @Select("SELECT c.id AS courseId, cu.name AS courseName, c.enrollment, c.capacity, u.nickname AS teacherName, " +
            "c.dept_name AS deptName, cu.credit, cu.introduction, " +
            "c.week_begin, c.week_end, c.day, c.section_begin, c.section_end, c.room, c.category_id " +
            "FROM SC sc " +
            "LEFT JOIN Course c ON sc.course_id = c.id " +
            "LEFT JOIN Curriculum cu ON c.curriculum_id = cu.id " +
            "LEFT JOIN sys_user u ON c.teacher_id = u.id " +
            "WHERE sc.student_id = #{studentId} AND sc.status = 1 " +
            "GROUP BY c.id")
    List<CourseVo> getJoinedCoursesByStudentId(@Param("studentId") Long studentId);

    @Select("SELECT c.id AS courseId, cu.name AS courseName, c.enrollment, c.capacity, u.nickname AS teacherName, " +
            "c.dept_name AS deptName, cu.credit, cu.introduction, " +
            "c.week_begin, c.week_end, c.day, c.section_begin, c.section_end, c.room, c.category_id " +
            "FROM Course c " +
            "LEFT JOIN Curriculum cu ON c.curriculum_id = cu.id " +
            "LEFT JOIN sys_user u ON c.teacher_id = u.id " +
            "WHERE c.teacher_id = #{teacherId}")
    List<CourseVo> getCourseVoByTeacherId(@Param("teacherId") Long teacherId);

    @Select("SELECT * FROM course WHERE teacher_id = #{teacherId}")
    List<Course> getCoursesByTeacherId(@Param("teacherId") Long teacherId);

    @Select("SELECT c.id AS courseId, cu.name AS courseName, c.enrollment, c.capacity, u.nickname AS teacherName, " +
            "c.dept_name AS deptName, cu.credit, cu.introduction, " +
            "c.week_begin, c.week_end, c.day, c.section_begin, c.section_end, c.room, c.category_id " +
            "FROM Assistant a " +
            "LEFT JOIN Course c ON a.course_id = c.id " +
            "LEFT JOIN Curriculum cu ON c.curriculum_id = cu.id " +
            "LEFT JOIN sys_user u ON c.teacher_id = u.id " +
            "WHERE a.student_id = #{assistantId} AND a.status = 1 " +
            "GROUP BY c.id")
    List<CourseVo> getCourseVoByAssistantId(@Param("assistantId") Long assistantId);
}
