package com.example.teachapi.service;

import com.example.teachapi.vo.CourseVo;
import com.example.teachapi.vo.TimetableVo;
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
class TeachingAssistantServiceTest {

    @Autowired
    private TeachingAssistantService teachingAssistantService;

    @Test
    void getAssistCoursesPositive() {
        Long id = 1L;
        List<CourseVo> data = teachingAssistantService.getAssistCourses(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    void getAssistCoursesNegative() {
        Long id = -1L;
        List<CourseVo> data = teachingAssistantService.getAssistCourses(id);
        Assertions.assertEquals(data.size(), 0);
    }
}