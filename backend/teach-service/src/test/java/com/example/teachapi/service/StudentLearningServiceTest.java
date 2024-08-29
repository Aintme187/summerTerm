package com.example.teachapi.service;

import com.example.teachapi.dao.pojo.Material;
import com.example.teachapi.vo.AssignmentVo;
import com.example.teachapi.vo.CourseVo;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.TimetableVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.RescaleOp;
import java.security.PrivilegedExceptionAction;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class StudentLearningServiceTest {

    @Autowired
    private StudentLearningService studentLearningService;

    @Test
    void getCourseTablePositive() {
        Long id = 10L;
        List<TimetableVo> data = studentLearningService.getCourseTable(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getCourseTableNegative() {
        Long id = -1L;
        List<TimetableVo> data = studentLearningService.getCourseTable(id);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    void applyForCourseEnrollmentPositive() {
        Long studentId = 1L;
        Long courseId = 2L;
        Result res = studentLearningService.applyForCourseEnrollment(studentId, courseId);
        Assertions.assertTrue(res.isSuccess());
    }

    @Test
    void getSelectedCoursesByStudentIdPositive() {
        Long id = 10L;
        List<CourseVo> data = studentLearningService.getSelectedCoursesByStudentId(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getMaterialsByCourseIdPositive() {
        Long id = 1L;
        List<Material> data = studentLearningService.getMaterialsByCourseId(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getMaterialsByCourseIdNegative() {
        Long id = -1L;
        List<Material> data = studentLearningService.getMaterialsByCourseId(id);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    void getAssignmentsPositive() {
        Long courseId = 1L;
        Long studentId = 1L;
        List<AssignmentVo> data = studentLearningService.getAssignments(courseId, studentId);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getAssignmentsNegative() {
        Long courseId = -1L;
        Long studentId = 2L;
        List<AssignmentVo> data = studentLearningService.getAssignments(courseId, studentId);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    void getJoinedCoursesPositive() {
        Long id = 10L;
        List<CourseVo> data = studentLearningService.getJoinedCourses(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getJoinedCoursesNegative() {
        Long id = -1L;
        List<CourseVo> data = studentLearningService.getJoinedCourses(id);
        Assertions.assertEquals(data.size(), 0);
    }

//    @Test
//    void getMaterialByArticleIdPositive() {
//        Long id = 1L;
//        List<Material> data = studentLearningService.getMaterialByArticleId(id);
//        Assertions.assertNotEquals(data.size(), 0);
//    }
//
//    @Test
//    void getMaterialByArticleIdNegative() {
//        Long id = -1L;
//        List<Material> data = studentLearningService.getMaterialByArticleId(id);
//        Assertions.assertEquals(data.size(), 0);
//    }
}