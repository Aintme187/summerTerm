package com.example.teachapi.controller;

import com.example.teachapi.dao.pojo.Material;
import com.example.teachapi.dao.pojo.Submission;
import com.example.teachapi.service.StudentLearningService;
import com.example.teachapi.service.TeacherTeachingService;
import com.example.teachapi.service.TeachingAssistantService;
import com.example.teachapi.vo.AssignmentVo;
import com.example.teachapi.vo.CourseVo;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.SubmissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Keith
 */
@RestController
@RequestMapping("/api/assistants")
public class TeachingAssistantController {

    @Autowired
    private TeacherTeachingService teacherTeachingService;
    @Autowired
    private StudentLearningService studentLearningService;
    @Autowired
    private TeachingAssistantService teachingAssistantService;

    @GetMapping("/assistCourses")
    public List<CourseVo> getAssistCourses(@RequestParam Long assistantId) {
        return teachingAssistantService.getAssistCourses(assistantId);
    }

    /**
     * 展示课程资料
     * @param courseId
     * @return
     */
    @GetMapping("/materials")
    public List<Material> getMaterials(@RequestParam Long courseId) {
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
     * @param courseId
     * @return
     */
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentVo>> getAssignments(@RequestParam Long courseId) {
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
     * @return {@link List<  Submission  >}
     * @throws
     */
    @GetMapping("/showSubmissions/")
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
    @GetMapping("/correctSubmission/")
    public Result correctSubmission(@RequestParam Long submissionId,
                                    @RequestParam Long score,
                                    @RequestParam MultipartFile feedbackFile,
                                    @RequestParam String comment) {
        return teacherTeachingService.correctSubmission(submissionId, score, feedbackFile, comment);
    }
}
