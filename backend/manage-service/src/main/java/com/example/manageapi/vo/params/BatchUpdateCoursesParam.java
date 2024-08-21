package com.example.manageapi.vo.params;

import com.example.manageapi.dao.pojo.Course;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCoursesParam {
    private List<Long> ids;
    private Course course;
}
