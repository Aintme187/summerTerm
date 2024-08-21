package com.example.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenterapi.dao.mapper.CategoryMapper;
import com.example.usercenterapi.dao.mapper.CourseMapper;
import com.example.usercenterapi.dao.mapper.CurriculumMapper;
import com.example.usercenterapi.dao.mapper.SysUserMapper;
import com.example.usercenterapi.dao.pojo.Category;
import com.example.usercenterapi.dao.pojo.Course;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.service.AdminCourseService;
import com.example.usercenterapi.vo.AdminCourseInfoVo;
import com.example.usercenterapi.vo.AdminCourseVo;
import com.example.usercenterapi.vo.ErrorCode;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.BatchUpdateCoursesParam;
import com.example.usercenterapi.vo.params.FilterData;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCourseServiceImpl implements AdminCourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CurriculumMapper curriculumMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    // 工厂方法（使用同一个 queryWrapper 会相互干扰）构建跨表查询，注意主表被重命名为 t
    private MPJQueryWrapper<Course> queryWrapperFactory() {
        return new MPJQueryWrapper<Course>()
                .selectAll(Course.class)
                .select("curriculum.name as curriculumName", "curriculum.credit as credit", "curriculum.introduction as introduction")
                .select("sys_user.nickname as teacherName")
                .leftJoin("curriculum on t.curriculum_id = curriculum.id")
                .leftJoin("sys_user on t.teacher_id = sys_user.id");
    }

    public static List<FilterData> mapFunction(List<FilterData> filterDataList) {
        for (FilterData filterData : filterDataList) {
            switch (filterData.getName()) {
                case "curriculumName":
                    filterData.setName("curriculum.name");
                    break;
                case "credit":
                case "introduction":
                    filterData.setName("curriculum." + filterData.getName());
                    break;
                case "teacherName":
                    filterData.setName("sys_user.nickname");
                    break;
                default:
                    filterData.setName("t." + filterData.getName());
                    break;
            }
        }
        return filterDataList;
    }

    @Override
    public Result listCoursePage(AdminPageParam adminPageParam) {
        MPJQueryWrapper<Course> queryWrapper = queryWrapperFactory();
        List<FilterData> filterDataList = adminPageParam.getFilterDataList();
        if (FilterData.injectFilter(queryWrapper, filterDataList, AdminCourseServiceImpl::mapFunction)) {
            AdminCourseVo adminCourseVo = new AdminCourseVo();
            Page<AdminCourseInfoVo> page = courseMapper.selectJoinPage(new Page<>(adminPageParam.getPage(), adminPageParam.getPageSize()), AdminCourseInfoVo.class, queryWrapper);
            adminCourseVo.setCourseVoList(page.getRecords());
            adminCourseVo.setCourseVoCount(page.getTotal());
            return Result.success(adminCourseVo);
        } else {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public Result getCourseInfoById(Long id) {
        Course course = courseMapper.selectById(id);
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
        String curriculumName = curriculumMapper.selectById(course.getCurriculumId()).getName();
        String nickname = sysUserMapper.selectById(course.getTeacherId()).getNickname();
        Category category = new Category(null, "http://seicj6zi6.hb-bkt.clouddn.com/%E8%BD%AF%E4%BB%B6%E5%AD%A6%E9%99%A2.jpg",
                curriculumName + "-" + nickname, curriculumName + "-" + nickname + "-" + course.getDeptName() + "-" + "课程讨论区");
        categoryMapper.insert(category);
        course.setCategoryId(category.getId());
        courseMapper.insert(course);
        return Result.success(null);
    }

    @Override
    public Result updateCourse(Course course) {
        Course oldCourse = courseMapper.selectById(course.getId());
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
        courseMapper.updateById(course);
        return Result.success(null);
    }

    @Override
    public Result deleteCourse(Long id) {
        if (courseMapper.selectById(id) == null) return Result.fail(ErrorCode.NO_CURRICULUM);
        courseMapper.deleteById(id);
        return Result.success(null);
    }

    @Override
    public Result batchDeleteCourses(List<Long> ids) {
        if (courseMapper.selectBatchIds(ids).size() != ids.size()) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            courseMapper.deleteBatchIds(ids);
            return Result.success(null);
        }
    }

    @Override
    public Result batchUpdateCourses(BatchUpdateCoursesParam batchUpdateCoursesParam) {
        List<Long> ids = batchUpdateCoursesParam.getIds();
        Course course = batchUpdateCoursesParam.getCourse();
        Long maxEnrollment = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MAX(enrollment) as enrollment")
                .in("id", ids)).getEnrollment();
        Long maxWeekBegin = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MAX(week_begin) as weekBegin")
                .in("id", ids)).getWeekBegin();
        Long minWeekEnd = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MIN(week_end) as weekEnd")
                .in("id", ids)).getWeekEnd();
        maxWeekBegin = course.getWeekBegin() == null ? maxWeekBegin : course.getWeekBegin();
        minWeekEnd = course.getWeekEnd() == null ? minWeekEnd : course.getWeekEnd();
        Long maxSectionBegin = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MAX(section_begin) as sectionBegin")
                .in("id", ids)).getSectionBegin();
        Long minSectionEnd = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MIN(section_end) as sectionEnd")
                .in("id", ids)).getSectionEnd();
        maxSectionBegin = course.getSectionBegin() == null ? maxSectionBegin : course.getSectionBegin();
        minSectionEnd = course.getSectionEnd() == null ? minSectionEnd : course.getSectionEnd();
        ErrorCode errorCode = errorInCourse(course, maxEnrollment);
        if (errorCode != null) {
            if (errorCode == ErrorCode.CAPACITY_LT_ENROLLMENT)
                return Result.fail(errorCode.getCode(), errorCode.getMsg() + maxEnrollment);
            return Result.fail(errorCode);
        }
        if ((course.getWeekBegin() != null || course.getWeekEnd() != null) && maxWeekBegin > minWeekEnd)
            return Result.fail(ErrorCode.ILLEGAL_WEEK);
        if ((course.getSectionBegin() != null || course.getSectionEnd() != null) && maxSectionBegin > minSectionEnd)
            return Result.fail(ErrorCode.ILLEGAL_SECTION);
        course.setId(null);
        course.setEnrollment(null);
        courseMapper.update(course, new LambdaQueryWrapper<Course>().in(Course::getId, ids));
        return Result.success(null);
    }

    private Course noNull(Course course) {
        Course newCourse = new Course(null, null, 0L, 1L, null, "", 1L, 1L, 1L, 1L, 1L, "", null);
        BeanUtils.copyProperties(course, newCourse);
        return newCourse;
    }

    private ErrorCode errorInCourse(Course course, Long enrollment) {
        Course oldCourse = null;
        if (course.getId() != null) oldCourse = courseMapper.selectById(course.getId());
        Long weekBegin = (course.getWeekBegin() == null && oldCourse != null) ? oldCourse.getWeekBegin() : course.getWeekBegin();
        Long weekEnd = (course.getWeekEnd() == null && oldCourse != null) ? oldCourse.getWeekEnd() : course.getWeekEnd();
        Long sectionBegin = (course.getSectionBegin() == null && oldCourse != null) ? oldCourse.getSectionBegin() : course.getSectionBegin();
        Long sectionEnd = (course.getSectionEnd() == null && oldCourse != null) ? oldCourse.getSectionEnd() : course.getSectionEnd();
        SysUser sysUser = null;
        if (course.getTeacherId() != null) sysUser = sysUserMapper.selectById(course.getTeacherId());

        if (course.getCurriculumId() != null && curriculumMapper.selectById(course.getCurriculumId()) == null)
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
