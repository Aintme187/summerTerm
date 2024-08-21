package com.example.teachapi.vo.params;

import com.example.teachapi.dao.pojo.Course;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCoursesParam {
    private List<Long> ids;
    private Course course;
}
