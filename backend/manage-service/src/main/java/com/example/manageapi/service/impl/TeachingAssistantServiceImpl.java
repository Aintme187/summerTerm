package com.example.manageapi.service.impl;

import com.example.manageapi.dao.mapper.CourseMapper;
import com.example.manageapi.service.TeachingAssistantService;
import com.example.manageapi.vo.CourseVo;
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
