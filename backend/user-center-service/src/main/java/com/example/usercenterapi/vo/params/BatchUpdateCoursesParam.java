package com.example.usercenterapi.vo.params;

import com.example.usercenterapi.dao.pojo.Course;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCoursesParam {
    private List<Long> ids;
    private Course course;
}
