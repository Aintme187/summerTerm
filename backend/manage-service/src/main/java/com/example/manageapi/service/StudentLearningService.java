package com.example.manageapi.service;

import com.example.manageapi.dao.pojo.Material;
import com.example.manageapi.vo.AssignmentVo;
import com.example.manageapi.vo.CourseVo;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.TimetableVo;
import com.example.manageapi.vo.params.StudentLearningPageParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 15252
 */
public interface StudentLearningService {
    // 获取课程表
    List<TimetableVo> getCourseTable(Long studentId);

    // 显示可选课程
    List<CourseVo> getAvailableCoursesByStudentId(Long studentId);

    // 根据课程名查找课程列表
    List<CourseVo> getCourseListByStudentIdAndCourseName(Long studentId, String courseName);

    // 学生申请加入课程
    Result applyForCourseEnrollment(Long courseId, Long studentId);

    // 查询已选课程
    List<CourseVo> getSelectedCoursesByStudentId(Long studentId);

    // 展示课程资料
    List<Material> getMaterialsByCourseId(Long courseId);

    // 获取下载链接
    ResponseEntity<Result> getMaterialDownloadUrl(Long materialId);
    ResponseEntity<Result> getAssignmentDownloadUrl(Long assignmentId);

    // 获取作业列表和作业详情
    List<AssignmentVo> getAssignments(Long courseId, Long studentId);

    // 提交作业
    Result submitAssignment(Long studentId, Long assignmentId, MultipartFile multipartFile);

    // 获取已加入课程
    List<CourseVo> getJoinedCourses(Long studentId);

    // 退课
    Result dropCourse(Long studentId, Long courseId);

    // 申请成为助教（可能需要管理员审批）
    Result applyToBeAssistant(Long studentId, Long courseId);

    Result listCoursePage(String token, StudentLearningPageParam studentLearningPageParam);

    List<Material> getMaterialByArticleId(Long articleId);

    ResponseEntity<Result> downloadMaterialForArticle(Long materialId);
}
