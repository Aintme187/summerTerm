package com.example.manageapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manageapi.client.BlogClient;
import com.example.manageapi.client.teachClient;
import com.example.manageapi.client.SysuserClient;
import com.example.manageapi.dao.dto.Category;
import com.example.manageapi.dao.dto.Course;
import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.service.AdminCourseService;
import com.example.manageapi.vo.AdminCourseInfoVo;
import com.example.manageapi.vo.AdminCourseVo;
import com.example.manageapi.vo.ErrorCode;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateCoursesParam;
import com.example.manageapi.vo.params.FilterData;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCourseServiceImpl implements AdminCourseService {
    @Autowired
    private SysuserClient sysuserClient;
    @Autowired
    private teachClient teachClient;
    @Autowired
    private BlogClient blogClient;

    @Override
    public Result listCoursePage(AdminPageParam adminPageParam) {
        return teachClient.selectJoinPage(adminPageParam);
    }

    @Override
    public Result getCourseInfoById(Long id) {
        Course course = teachClient.selectById(id);
        if (course == null) return Result.fail(ErrorCode.NO_COURSE);
        return Result.success(course);
    }

    @Override
    public Result addCourse(Course course) {
        course = noNull(course);
        ErrorCode errorCode = errorInCourse(course, null);
        if (errorCode != null) {
            return Result.fail(errorCode);
        }
        course.setId(null);
        course.setEnrollment(0L);
        if (course.getDeptName() == null) course.setDeptName("");
        if (course.getRoom() == null) course.setRoom("");
        course = noNull(course);
        String curriculumName = teachClient.selectCurriculumById(course.getCurriculumId()).getName();
        String nickname = sysuserClient.selectById(course.getTeacherId()).getNickname();
        Category category = new Category(null, "http://seicj6zi6.hb-bkt.clouddn.com/%E8%BD%AF%E4%BB%B6%E5%AD%A6%E9%99%A2.jpg",
                curriculumName + "-" + nickname, curriculumName + "-" + nickname + "-" + course.getDeptName() + "-" + "课程讨论区");
        blogClient.insert(category);
        course.setCategoryId(category.getId());
        course.setTeacherName(nickname);
        teachClient.insert(course);
        return Result.success(null);
    }

    @Override
    public Result updateCourse(Course course) {
        Course oldCourse = teachClient.selectById(course.getId());
        if (oldCourse == null) return Result.fail(ErrorCode.NO_COURSE);
        ErrorCode errorCode = errorInCourse(course, oldCourse.getEnrollment());
        if (errorCode != null) {
            if (errorCode == ErrorCode.CAPACITY_LT_ENROLLMENT)
                return Result.fail(errorCode.getCode(), errorCode.getMsg() + oldCourse.getEnrollment());
            return Result.fail(errorCode);
        }
        course.setEnrollment(null);
        if (course.getDeptName() == null) course.setDeptName("");
        if (course.getRoom() == null) course.setRoom("");
        teachClient.updateById(course);
        return Result.success(null);
    }

    @Override
    public Result deleteCourse(Long id) {
        if (teachClient.selectById(id) == null) return Result.fail(ErrorCode.NO_CURRICULUM);
        teachClient.deleteById(id);
        return Result.success(null);
    }

    @Override
    public Result batchDeleteCourses(List<Long> ids) {
        if (teachClient.selectBatchIds(ids).size() != ids.size()) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            teachClient.deleteBatchIds(ids);
            return Result.success(null);
        }
    }

    @Override
    public Result batchUpdateCourses(BatchUpdateCoursesParam batchUpdateCoursesParam) {
        return teachClient.update(batchUpdateCoursesParam);
    }

    private Course noNull(Course course) {
        Course newCourse = new Course(null, null, 0L, 1L, null, "", 1L, 1L, 1L, 1L, 1L, "", null, "");
        BeanUtils.copyProperties(course, newCourse);
        return newCourse;
    }

    private ErrorCode errorInCourse(Course course, Long enrollment) {
        Course oldCourse = null;
        if (course.getId() != null) oldCourse = teachClient.selectById(course.getId());
        Long weekBegin = (course.getWeekBegin() == null && oldCourse != null) ? oldCourse.getWeekBegin() : course.getWeekBegin();
        Long weekEnd = (course.getWeekEnd() == null && oldCourse != null) ? oldCourse.getWeekEnd() : course.getWeekEnd();
        Long sectionBegin = (course.getSectionBegin() == null && oldCourse != null) ? oldCourse.getSectionBegin() : course.getSectionBegin();
        Long sectionEnd = (course.getSectionEnd() == null && oldCourse != null) ? oldCourse.getSectionEnd() : course.getSectionEnd();
        SysUser sysUser = null;
        if (course.getTeacherId() != null) sysUser = sysuserClient.selectById(course.getTeacherId());

        if (course.getCurriculumId() != null && teachClient.selectCurriculumById(course.getCurriculumId()) == null)
            return ErrorCode.NO_CURRICULUM;
        if (course.getCapacity() != null && (course.getCapacity() <= 0 || (enrollment != null && course.getCapacity() < enrollment)))
            return ErrorCode.CAPACITY_LT_ENROLLMENT;
        if (sysUser != null && !sysUser.getPermission().equals("teacher")) return ErrorCode.NOT_A_TEACHER;
        if ((course.getWeekBegin() != null && course.getWeekBegin() < 1)
                || (course.getWeekEnd() != null && course.getWeekEnd() < 1)
                || (weekBegin != null && weekEnd != null && weekBegin > weekEnd)) return ErrorCode.ILLEGAL_WEEK;
        if ((course.getSectionBegin() != null && (course.getSectionBegin() < 1 || course.getSectionBegin() > 13))
                || (course.getSectionEnd() != null && (course.getSectionEnd() < 1 || course.getSectionEnd() > 13))
                || (sectionBegin != null && sectionEnd != null && sectionBegin > sectionEnd))
            return ErrorCode.ILLEGAL_SECTION;
        if (course.getDay() != null && (course.getDay() < 1 || course.getDay() > 7)) return ErrorCode.ILLEGAL_WEEKDAY;
        return null;
    }
}
