package com.example.teachapi.service;

import com.example.teachapi.vo.AssignmentVo;
import com.example.teachapi.vo.CourseVo;
import com.example.teachapi.vo.SubmissionVo;
import com.example.teachapi.vo.TimetableVo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class TeacherTeachingServiceTest {
    @Autowired
    private TeacherTeachingService teacherTeachingService;

    @Test
    public void getCourseTablePositive() {
        Long id = 4L;
        List<TimetableVo> data = teacherTeachingService.getCourseTable(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void getCourseTableNegative() {
        Long id = -1L;
        List<TimetableVo> data = teacherTeachingService.getCourseTable(id);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    public void getTeachingCoursesPositive() {
        Long id = 4L;
        List<CourseVo> data = teacherTeachingService.getTeachingCourses(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void getTeachingCoursesNegative() {
        Long id = -1L;
        List<CourseVo> data = teacherTeachingService.getTeachingCourses(id);
        Assertions.assertEquals(data.size(), 0);
    }

//    @Test
//    public void showScApplicationPositive() {
//        Long id = 4L;
//        List<ScApplicationVo> data = teacherTeachingService.showScApplication(id);
//        Assertions.assertNotEquals(data.size(), 0);
//    }
//
//    @Test
//    public void showScApplicationNegative() {
//        Long id = -1L;
//        List<ScApplicationVo> data = teacherTeachingService.showScApplication(id);
//        Assertions.assertEquals(data.size(), 0);
//    }

//    @Test
//    public void showAssistantApplicationPositive() {
//        Long id = 4L;
//        List<AssistantApplicationVo> data = teacherTeachingService.showAssistantApplication(id);
//        Assertions.assertNotEquals(data.size(), 0);
//    }
//
//    @Test
//    public void showAssistantApplicationNegative() {
//        Long id = -1L;
//        List<AssistantApplicationVo> data = teacherTeachingService.showAssistantApplication(id);
//        Assertions.assertEquals(data.size(), 0);
//    }

    @Test
    public void getAssignmentsPositive() {
        Long id = 4L;
        List<AssignmentVo> data = teacherTeachingService.getAssignments(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void getAssignmentsNegative() {
        Long id = -1L;
        List<AssignmentVo> data = teacherTeachingService.getAssignments(id);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    public void getCourseAssignmentsPositive() {
        Long courseId = 1L;
        List<AssignmentVo> data = teacherTeachingService.getCourseAssignments(courseId);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void getCourseAssignmentsNegative() {
        Long courseId = -1L;
        List<AssignmentVo> data = teacherTeachingService.getCourseAssignments(courseId);
        Assertions.assertEquals(data.size(), 0);
    }

    @Test
    public void showSubmissionsPositive() {
        Long assignmentId = 1L;
        List<SubmissionVo> data = teacherTeachingService.showSubmissions(assignmentId);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void showSubmissionsNegative() {
        Long assignmentId = -1L;
        List<SubmissionVo> data = teacherTeachingService.showSubmissions(assignmentId);
        Assertions.assertEquals(data.size(), 0);
    }

//    @Test
//    public void modifyCourseAssistantsNegative() {
//        Long studentId = 7L;
//        Long teacherId = 4L;
//        Result res = teacherTeachingService.modifyCourseAssistants(studentId, teacherId, true);
//        Assertions.assertFalse(res.isSuccess());
//    }
}