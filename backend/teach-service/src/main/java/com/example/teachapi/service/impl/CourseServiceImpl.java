package com.example.teachapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.client.SysUserClient;
import com.example.teachapi.dao.dto.AdminCourseInfoVo;
import com.example.teachapi.dao.dto.SysUser;
import com.example.teachapi.dao.mapper.CourseMapper;
import com.example.teachapi.dao.mapper.CurriculumMapper;
import com.example.teachapi.dao.pojo.Course;
import com.example.teachapi.service.CourseService;
import com.example.teachapi.vo.AdminCourseVo;
import com.example.teachapi.vo.ErrorCode;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.example.teachapi.vo.params.BatchUpdateCoursesParam;
import com.example.teachapi.vo.params.FilterData;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * @author ddddm
 **/
@CrossOrigin
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CurriculumMapper curriculumMapper;
    @Autowired
    private SysUserClient sysuserClient;
    @Override
    public Course selectById(Long id) {
        return courseMapper.selectById(id);
    }

    // 工厂方法（使用同一个 queryWrapper 会相互干扰）构建跨表查询，注意主表被重命名为 t
    private MPJQueryWrapper<Course> queryWrapperFactory() {
        return new MPJQueryWrapper<Course>()
                .selectAll(Course.class)
                .select("curriculum.name as curriculumName", "curriculum.credit as credit", "curriculum.introduction as introduction")
                .leftJoin("curriculum on t.curriculum_id = curriculum.id");
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
    public Result selectJoinPage(AdminPageParam adminPageParam){
        MPJQueryWrapper<Course> queryWrapper = queryWrapperFactory();
        List<FilterData> filterDataList = adminPageParam.getFilterDataList();
        if (FilterData.injectFilter(queryWrapper, filterDataList, CourseServiceImpl::mapFunction)) {
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
    public void insert(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void updateById(Course course) {
        courseMapper.updateById(course);
    }

    @Override
    public Result deleteById(Long id) {
        try {
            courseMapper.deleteById(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public List<Course> selectBatchIds(List<Long> ids) {
        return courseMapper.selectBatchIds(ids);
    }

    @Override
    public Result deleteBatchIds(List<Long> ids) {
        try {
            courseMapper.deleteBatchIds(ids);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public Result update(BatchUpdateCoursesParam batchUpdateCoursesParam) {
        List<Long> ids = batchUpdateCoursesParam.getIds();
        Course course = batchUpdateCoursesParam.getCourse();
        Long maxEnrollment = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MAX(enrollment) as enrollment")
                .in("id", ids)
        ).getEnrollment();
        Long maxWeekBegin = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MAX(week_begin) as weekBegin")
                .in("id", ids)
        ).getWeekBegin();
        Long minWeekEnd = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MIN(week_end) as weekEnd")
                .in("id", ids)
        ).getWeekEnd();
        maxWeekBegin = course.getWeekBegin() == null ? maxWeekBegin : course.getWeekBegin();
        minWeekEnd = course.getWeekEnd() == null ? minWeekEnd : course.getWeekEnd();
        Long maxSectionBegin = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MAX(section_begin) as sectionBegin")
                .in("id", ids)
        ).getSectionBegin();
        Long minSectionEnd = courseMapper.selectOne(new QueryWrapper<Course>()
                .select("MIN(section_end) as sectionEnd")
                .in("id", ids)
        ).getSectionEnd();
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
        courseMapper.update(course, new LambdaUpdateWrapper<Course>().in(Course::getId, ids));
        return Result.success(null);
    }

    private ErrorCode errorInCourse(Course course, Long enrollment) {
        Course oldCourse = null;
        if (course.getId() != null) oldCourse = courseMapper.selectById(course.getId());
        Long weekBegin = (course.getWeekBegin() == null && oldCourse != null) ? oldCourse.getWeekBegin() : course.getWeekBegin();
        Long weekEnd = (course.getWeekEnd() == null && oldCourse != null) ? oldCourse.getWeekEnd() : course.getWeekEnd();
        Long sectionBegin = (course.getSectionBegin() == null && oldCourse != null) ? oldCourse.getSectionBegin() : course.getSectionBegin();
        Long sectionEnd = (course.getSectionEnd() == null && oldCourse != null) ? oldCourse.getSectionEnd() : course.getSectionEnd();
        SysUser sysUser = null;
        if (course.getTeacherId() != null) sysUser = sysuserClient.selectById(course.getTeacherId());

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
