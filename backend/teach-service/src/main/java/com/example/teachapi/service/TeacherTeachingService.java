package com.example.teachapi.service;

import com.example.teachapi.vo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 15252
 */
public interface TeacherTeachingService {
    // 查看课程表
    List<TimetableVo> getCourseTable(Long teacherId);
    // 查看所教课程
    List<CourseVo> getTeachingCourses(Long teacherId);
    // 显示学生选课申请
    List<ScApplicationVo> showScApplication(Long teacherId);
    // 显示助教申请
    List<AssistantApplicationVo> showAssistantApplication(Long teacherId);
    // 增删学生
    Result modifyCourseParticipants(Long courseId, Long studentId, boolean operation);
    // 增删助教
    Result modifyCourseAssistants(Long courseId, Long studentId, boolean operation);
    // 获取作业列表和作业详情
    List<AssignmentVo> getAssignments(Long teacherId);
    List<AssignmentVo> getCourseAssignments(Long courseId);
    // 发布课程作业
    Result releaseAssignment(String name, String description, LocalDateTime beginTime,
                             LocalDateTime endTime, Long courseId, MultipartFile multipartFile);
    // 查看作业
    List<SubmissionVo> showSubmissions(Long assignmentId);
    // 获取提交文件下载链接
    ResponseEntity<Result> downloadSubmission(Long submissionId);
    // 批改作业
    Result correctSubmission(Long submissionId, Long score, MultipartFile feedbackFile, String comment);
    // 上传课程资料
    Result submitMaterial(Long courseId, MultipartFile multipartFile);
}
