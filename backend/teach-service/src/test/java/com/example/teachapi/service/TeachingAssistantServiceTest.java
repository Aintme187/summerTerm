package com.example.teachapi.service;

import com.example.teachapi.vo.CourseVo;
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
public class TeachingAssistantServiceTest {

    @Autowired
    private TeachingAssistantService teachingAssistantService;

    @Test
    public void getAssistCoursesPositive() {
        Long id = 1L;
        List<CourseVo> data = teachingAssistantService.getAssistCourses(id);
        Assertions.assertNotEquals(data.size(), 0);
    }

    @Test
    public void getAssistCoursesNegative() {
        Long id = -1L;
        List<CourseVo> data = teachingAssistantService.getAssistCourses(id);
        Assertions.assertEquals(data.size(), 0);
    }
}