package com.example.teachapi.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.dao.dto.AdminCourseInfoVo;
import com.example.teachapi.dao.dto.SysUser;
import com.example.teachapi.dao.pojo.Course;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.example.teachapi.vo.params.BatchUpdateCoursesParam;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface CourseService {
    Course selectById(Long id);

    Result selectJoinPage(AdminPageParam adminPageParam);

    void insert(Course course);

    void updateById(Course course);

    Result deleteById(Long id);

    List<Course> selectBatchIds(List<Long> ids);

    Result deleteBatchIds(List<Long> ids);

    Result update(BatchUpdateCoursesParam batchUpdateCoursesParam);
}
