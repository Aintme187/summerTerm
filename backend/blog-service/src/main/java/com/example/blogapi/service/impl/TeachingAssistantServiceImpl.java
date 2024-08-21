package com.example.blogapi.service.impl;

import com.example.blogapi.dao.mapper.CourseMapper;
import com.example.blogapi.service.TeachingAssistantService;
import com.example.blogapi.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ddddm
 **/
@Service
public class TeachingAssistantServiceImpl implements TeachingAssistantService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseVo> getAssistCourses(Long assistantId) {
        return courseMapper.getCourseVoByAssistantId(assistantId);
    }
}
