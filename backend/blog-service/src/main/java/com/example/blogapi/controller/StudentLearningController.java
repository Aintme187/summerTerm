package com.example.blogapi.controller;

import com.example.blogapi.common.aop.LogAnnotation;
import com.example.blogapi.dao.pojo.Material;
import com.example.blogapi.service.StudentLearningService;
import com.example.blogapi.vo.*;
import com.example.blogapi.vo.params.StudentLearningPageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Keith
 */
@RestController
@RequestMapping("/api/students")
public class StudentLearningController {
    @Autowired
    private StudentLearningService studentLearningService;

    /**
     * 获取课程表
     * @param studentId
     * @return
     */
    @GetMapping("/course-table")
    public List<TimetableVo> getStudentCourseTable(@RequestParam Long studentId) {
        return studentLearningService.getCourseTable(studentId);
    }

    @PostMapping("listCourse")
    @LogAnnotation(module = "管理教学班", operator = "获取教学班列表")
    public Result courses(@RequestHeader("Authorization") String token, @RequestBody StudentLearningPageParam studentLearningPageParam) {
        return studentLearningService.listCoursePage(token, studentLearningPageParam);
    }
    /**
     * 筛选课程
     * @param token
     * @param studentLearningPageParam
     * @return
     */
    /*@PostMapping("/listCourse")
    @LogAnnotation(module = "管理教学班", operator = "获取教学班列表")
    public Result courses(@RequestHeader("Authorization") String token, @RequestBody StudentLearningPageParam studentLearningPageParam) {
        return studentLearningService.listCoursePage(token, studentLearningPageParam);
    }*/

    /**
     * 申请成为助教
     * @param studentId
     * @param courseId
     * @return
     */
    @PostMapping("/applyToBeAssistant")
    public Result applyToBeAssistant(@RequestParam Long studentId,
                                     @RequestParam Long courseId) {
        return studentLearningService.applyToBeAssistant(studentId, courseId);
    }

    /**
     * 选课：申请加入课程
     * @param courseId
     * @param studentId
     * @return
     */
    @PostMapping("/applyForCourseEnrollment")
    public Result applyForCourseEnrollment(@RequestParam Long courseId,
                                           @RequestParam Long studentId) {
        return studentLearningService.applyForCourseEnrollment(courseId, studentId);
    }

    /**
     * 查询已选课程
     * @param studentId
     * @return
     */
    @GetMapping("/selectedCourses")
    public List<CourseVo> getSelectedCourses(@RequestParam Long studentId) {
        return studentLearningService.getSelectedCoursesByStudentId(studentId);
    }

    /**
     * 退课
     * @param studentId
     * @param courseId
     * @return
     */
    @PostMapping("/dropCourse")
    public Result dropCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        return studentLearningService.dropCourse(studentId, courseId);
    }

    /**
     * 获取已加入课程
     * @param studentId
     * @return
     */
    @GetMapping("/myCourse")
    public List<CourseVo> getJoinedCourses(@RequestParam Long studentId) {
        return studentLearningService.getJoinedCourses(studentId);
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
    @GetMapping("/materials/article")
    public List<Material> getMaterialByArticleId(@RequestParam Long articleId) {
        return studentLearningService.getMaterialByArticleId(articleId);
    }

    /**
     * 获取课程资料打开链接
     * @param materialId
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<Result> downloadMaterial(@RequestParam Long materialId) {
        ResponseEntity<Result> res = studentLearningService.getMaterialDownloadUrl(materialId);
        return res;
    }
    @GetMapping("/download/article")
    public ResponseEntity<Result> downloadMaterialForArticle(@RequestParam Long materialId) {
        return studentLearningService.downloadMaterialForArticle(materialId);
    }

    /**
     * 获取作业列表和作业详情
     * @param courseId
     * @param studentId
     * @return
     */
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentVo>> getAssignments(@RequestParam Long courseId, @RequestParam Long studentId) {
        return ResponseEntity.ok(studentLearningService.getAssignments(courseId, studentId));
    }

    /**
     * 获取课程作业打开链接
     * @param assignmentId
     * @return
     */
    @GetMapping("/assignments/download")
    public ResponseEntity<Result> downloadAssignment(@RequestParam Long assignmentId) {
        return studentLearningService.getAssignmentDownloadUrl(assignmentId);
    }

    /**
     * 提交课程作业
     * @param studentId
     * @param assignmentId
     * @param multipartFile
     * @return
     */
    @PostMapping("/submitAssignment")
    public Result submitAssignment(@RequestParam Long studentId,
                                   @RequestParam Long assignmentId,
                                   @RequestParam MultipartFile multipartFile) {
        return studentLearningService.submitAssignment(studentId, assignmentId, multipartFile);
    }


    /**
     * 显示可选课程
     * @param studentId
     * @return
     */
    @GetMapping("/getAvailableCourses")
    public List<CourseVo> getAvailableCourses(@RequestParam Long studentId) {
        return studentLearningService.getAvailableCoursesByStudentId(studentId);
    }
}
