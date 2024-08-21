package com.example.blogapi.vo.params;

import com.example.blogapi.dao.pojo.Course;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCoursesParam {
    private List<Long> ids;
    private Course course;
}
