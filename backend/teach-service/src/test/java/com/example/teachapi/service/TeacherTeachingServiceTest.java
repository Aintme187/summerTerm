package com.example.teachapi.service;

import com.example.teachapi.vo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class TeacherTeachingServiceTest {
    @Autowired
    private TeacherTeachingService teacherTeachingService;

    @Test
    void getCourseTablePositive() {
        Long id = 4L;
        List<TimetableVo> data = teacherTeachingService.getCourseTable(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getCourseTableNegative() {
        Long id = -1L;
        List<TimetableVo> data = teacherTeachingService.getCourseTable(id);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    void getTeachingCoursesPositive() {
        Long id = 4L;
        List<CourseVo> data = teacherTeachingService.getTeachingCourses(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getTeachingCoursesNegative() {
        Long id = -1L;
        List<CourseVo> data = teacherTeachingService.getTeachingCourses(id);
        Assertions.assertEquals(data.size(), 0);
    }

//    @Test
//    void showScApplicationPositive() {
//        Long id = 4L;
//        List<ScApplicationVo> data = teacherTeachingService.showScApplication(id);
//        Assertions.assertNotEquals(data.size(), 0);
//    }
//
//    @Test
//    void showScApplicationNegative() {
//        Long id = -1L;
//        List<ScApplicationVo> data = teacherTeachingService.showScApplication(id);
//        Assertions.assertEquals(data.size(), 0);
//    }

//    @Test
//    void showAssistantApplicationPositive() {
//        Long id = 4L;
//        List<AssistantApplicationVo> data = teacherTeachingService.showAssistantApplication(id);
//        Assertions.assertNotEquals(data.size(), 0);
//    }
//
//    @Test
//    void showAssistantApplicationNegative() {
//        Long id = -1L;
//        List<AssistantApplicationVo> data = teacherTeachingService.showAssistantApplication(id);
//        Assertions.assertEquals(data.size(), 0);
//    }

    @Test
    void getAssignmentsPositive() {
        Long id = 4L;
        List<AssignmentVo> data = teacherTeachingService.getAssignments(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getAssignmentsNegative() {
        Long id = -1L;
        List<AssignmentVo> data = teacherTeachingService.getAssignments(id);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    void getCourseAssignmentsPositive() {
        Long courseId = 1L;
        List<AssignmentVo> data = teacherTeachingService.getCourseAssignments(courseId);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getCourseAssignmentsNegative() {
        Long courseId = -1L;
        List<AssignmentVo> data = teacherTeachingService.getCourseAssignments(courseId);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    void showSubmissionsPositive() {
        Long assignmentId = 1L;
        List<SubmissionVo> data = teacherTeachingService.showSubmissions(assignmentId);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void showSubmissionsNegative() {
        Long assignmentId = -1L;
        List<SubmissionVo> data = teacherTeachingService.showSubmissions(assignmentId);
        Assertions.assertEquals(data.size(), 0);
    }

//    @Test
//    void modifyCourseAssistantsNegative() {
//        Long studentId = 7L;
//        Long teacherId = 4L;
//        Result res = teacherTeachingService.modifyCourseAssistants(studentId, teacherId, true);
//        Assertions.assertFalse(res.isSuccess());
//    }
}