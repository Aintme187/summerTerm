package com.example.blogapi.controller;

import com.example.blogapi.dao.pojo.*;
import com.example.blogapi.service.StudentLearningService;
import com.example.blogapi.service.TeacherTeachingService;
import com.example.blogapi.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author DdddM123
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherTeachingController {

    @Autowired
    private TeacherTeachingService teacherTeachingService;
    @Autowired
    private StudentLearningService studentLearningService;

    /**
     * 获取课程表
     * @param teacherId
     * @return {@link List<TimetableVo>}
     */
    @GetMapping("/courseTable")
    public List<TimetableVo> getTeacherCourseTable(@RequestParam Long teacherId) {
        return teacherTeachingService.getCourseTable(teacherId);
    }

    /**
     * 获取待处理学生选课申请列表
     * @param teacherId
     * @return {@link List<ScApplicationVo>}
     */
    @GetMapping("/showScApplication")
    public List<ScApplicationVo> showScApplication(@RequestParam Long teacherId) {
        return teacherTeachingService.showScApplication(teacherId);
    }

    /**
     * 获取待处理助教申请列表
     * @param teacherId
     * @return {@link List<AssistantApplicationVo>}
     */
    @GetMapping("/showAssistantApplication")
    public List<AssistantApplicationVo> showAssistantApplication(@RequestParam Long teacherId) {
        return teacherTeachingService.showAssistantApplication(teacherId);
    }

    /**
     * 展示所教授课程
     * @param teacherId
     * @return
     */
    @GetMapping("/teachingCourses")
    public List<CourseVo> getTeachingCourses(@RequestParam Long teacherId) {
        return teacherTeachingService.getTeachingCourses(teacherId);
    }

    /**
     * 展示课程资料
     * @param courseId
     * @return
     */
    @GetMapping("/materials")
    public List<Material> getMaterialByCourseId(@RequestParam Long courseId) {
        return studentLearningService.getMaterialsByCourseId(courseId);
    }

    /**
     * 上传课程资料
     * @param courseId
     * @param multipartFile
     * @return
     */
    @PostMapping("/submitMaterial")
    public Result submitMaterial(@RequestParam Long courseId,
                                   @RequestParam MultipartFile multipartFile) {
        return teacherTeachingService.submitMaterial(courseId, multipartFile);
    }

    /**
     * 获取作业列表和作业详情
     * @param teacherId
     * @return
     */
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentVo>> getAssignments(@RequestParam Long teacherId) {
        return ResponseEntity.ok(teacherTeachingService.getAssignments(teacherId));
    }

    /**
     * 获取某课程的作业列表
     * @param courseId
     * @return
     */
    @GetMapping("/courseAssignments")
    public ResponseEntity<List<AssignmentVo>> getCourseAssignments(@RequestParam Long courseId) {
        return ResponseEntity.ok(teacherTeachingService.getCourseAssignments(courseId));
    }

    /**
     * 布置作业
     * @param name
     * @param description
     * @param beginTime
     * @param endTime
     * @param courseId
     * @param multipartFile
     * @return
     */
    @PostMapping("/releaseAssignment")
    public Result releaseAssignment(@RequestParam String name, @RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime, @RequestParam Long courseId, @RequestParam MultipartFile multipartFile) {
        return teacherTeachingService.releaseAssignment(name, description, beginTime, endTime, courseId, multipartFile);
    }

    /**
     * 查看作业的提交
     * @param assignmentId
     * @return {@link List<SubmissionVo>}
     */
    @GetMapping("/showSubmissions")
    public List<SubmissionVo> showSubmissions(@RequestParam Long assignmentId) {
        return teacherTeachingService.showSubmissions(assignmentId);
    }
    
    /**
     * 获取提交文件的下载链接
     * @param submissionId
     * @return {@link ResponseEntity<Result>}
     */
    @GetMapping("/downloadSubmission")
    public ResponseEntity<Result> downloadSubmission(@RequestParam Long submissionId) {
        return teacherTeachingService.downloadSubmission(submissionId);
    }
    
    /**
     * 批改作业
     * @param submissionId
     * @param score
     * @param feedbackFile
     * @param comment 
     * @return {@link Result}
     */
    @PostMapping("/correctSubmission")
    public Result correctSubmission(@RequestParam Long submissionId,
                                    @RequestParam Long score,
                                    @RequestParam MultipartFile feedbackFile,
                                    @RequestParam String comment) {
        return teacherTeachingService.correctSubmission(submissionId, score, feedbackFile, comment);
    }

    /**
     * 增删学生
     * @param courseId
     * @param participantId
     * @param operation
     * @return {@link com.example.blogapi.vo.Result}
     */
    @PostMapping("/participantsModify")
    public Result modifyCourseParticipants(@RequestParam Long courseId,
                                           @RequestParam Long participantId,
                                           @RequestParam boolean operation) {
        return teacherTeachingService.modifyCourseParticipants(courseId, participantId, operation);
    }

    /**
     * 增删助教
     * @param courseId
     * @param assistantId
     * @param operation
     * @return {@link com.example.blogapi.vo.Result}
     */
    @PostMapping("/assistantsModify")
    public Result modifyCourseAssistants(@RequestParam Long courseId,
                                         @RequestParam Long assistantId,
                                         @RequestParam boolean operation) {
        return teacherTeachingService.modifyCourseAssistants(courseId, assistantId, operation);
    }
}