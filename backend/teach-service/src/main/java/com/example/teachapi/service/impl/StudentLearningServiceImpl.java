package com.example.teachapi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachapi.client.SysUserClient;
import com.example.teachapi.config.TeachingModuleConfig;
import com.example.teachapi.dao.dto.SysUser;
import com.example.teachapi.service.FileServiceForTeachingModule;
import com.example.teachapi.service.LoginService;
import com.example.teachapi.service.StudentLearningService;
import com.example.teachapi.utils.QiniuUtilsForTeaching;
import com.example.teachapi.vo.params.AdminPageParam;
import com.example.teachapi.vo.params.FilterData;
import com.example.teachapi.vo.params.StudentLearningPageParam;
import com.example.teachapi.dao.mapper.*;
import com.example.teachapi.dao.pojo.*;
import com.example.teachapi.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yulichang.query.MPJQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Keith
 */
@Service
public class StudentLearningServiceImpl implements StudentLearningService {

    @Autowired
    private SCMapper scMapper;
    @Autowired
    private FileServiceForTeachingModule fileServiceForTeachingModule;
    @Autowired
    private SubmissionMapper submissionMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private AssistantMapper assistantMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private CurriculumMapper curriculumMapper;
    @Autowired
    private QiniuUtilsForTeaching qiniuUtilsForTeaching;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ArticleMaterialMapper articleMaterialMapper;
    @Autowired
    private SysUserClient sysUserClient;

    @Override
    public List<TimetableVo> getCourseTable(Long studentId) {
        List<Sc> scList = scMapper.selectJoinedCoursesByStudentId(studentId);
        List<TimetableVo> timetable = new ArrayList<>();

        for (Sc sc : scList) {
            Course course = courseMapper.selectById(sc.getCourseId());
            Curriculum curriculum = curriculumMapper.selectCurriculumById(course.getCurriculumId());

            TimetableVo vo = new TimetableVo();
            vo.setDay(course.getDay());
            vo.setName(curriculum.getName());
            vo.setContent(String.format("%d-%d周 %s %s",
                    course.getWeekBegin(),
                    course.getWeekEnd(),
                    course.getRoom(),
                    course.getTeacherName()
                    )
            ); // 假设 "哈哈哈" 是教师姓名，需要从教师表中获取具体的教师姓名
            vo.setSectionBegin(course.getSectionBegin());
            vo.setSectionEnd(course.getSectionEnd());

            timetable.add(vo);
        }

        return timetable;
    }

    @Override
    public List<CourseVo> getAvailableCoursesByStudentId(Long studentId) {
        // 1. 查出所有的课程（排除已选课程）
        List<CourseVo> courseVos = courseMapper.getAvailableCoursesByStudentId(studentId);

        // 2. 对 timeAndRoom 进行处理
        for (CourseVo courseVo : courseVos) {
            List<String> timeAndRooms = courseMapper.getTimeAndRoom(courseVo.getCourseId());
            String timeAndRoomStr = String.join("; ", timeAndRooms);
            // courseVo.setTimeAndRoom(timeAndRoomStr);
        }

        return courseVos;
    }

    @Override
    public List<CourseVo> getCourseListByStudentIdAndCourseName(Long studentId, String courseName) {
        // 1. 查出所有的相同名称的课程（排除已选课程）
        List<CourseVo> courseVos = courseMapper.getAvailableCoursesByStudentIdAndCourseName(studentId, courseName);

        // 2. 对 timeAndRoom 进行处理
        for (CourseVo courseVo : courseVos) {
            List<String> timeAndRooms = courseMapper.getTimeAndRoom(courseVo.getCourseId());
            String timeAndRoomStr = String.join("; ", timeAndRooms);
            // courseVo.setTimeAndRoom(timeAndRoomStr);
        }

        return courseVos;
    }

    @Override
    public Result applyForCourseEnrollment(Long courseId, Long studentId) {
        // 创建新记录
        Sc sc = new Sc();
        sc.setStudentId(studentId);
        sc.setCourseId(courseId);
        sc.setStatus(TeachingModuleConfig.JOINING_COURSE);
        sc.setScore(0);
        // 插入记录到Assistant表
        int rowsAffected = scMapper.insert(sc);
        if (rowsAffected > 0) {
            return Result.success("申请加入课程成功");
        } else {
            return Result.fail(404, "申请失败，请重试");
        }
    }

    @Override
    public List<CourseVo> getSelectedCoursesByStudentId(Long studentId) {
        return courseMapper.getSelectedCoursesByStudentId(studentId);
    }


    @Override
    public Result dropCourse(Long studentId, Long courseId) {
        // 调用自定义方法
        int result = scMapper.deleteById(scMapper.selectByStudentIdAndCourseId(studentId, courseId).getId());

        if (result > 0) {
            Course course = courseMapper.selectById(courseId);
            course.setEnrollment(course.getEnrollment() - 1);
            return Result.success("退课成功");
        } else {
            return Result.fail(404, "退课失败");
        }
    }

    @Override
    public List<Material> getMaterialsByCourseId(Long courseId) {
        return materialMapper.findMaterialsByCourseId(courseId);
    }

    @Override
    public ResponseEntity<Result> getMaterialDownloadUrl(Long materialId) {
        try {
            // Retrieve the material details by its ID
            Material material = materialMapper.selectById(materialId);

            // Generate the download URL for the material
            String downloadUrl = fileServiceForTeachingModule.generateDownloadUrl(material.getUrl());

            // Return the download URL in the response
            return ResponseEntity.ok(Result.success(downloadUrl));
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(500, "Failed to generate download URL"));
        }
    }

    @Override
    public List<AssignmentVo> getAssignments(Long courseId, Long studentId) {
        List<AssignmentVo> assignments = assignmentMapper.getAssignmentsByCourseId(courseId);
        for (AssignmentVo assignment : assignments) {
            Submission submission = submissionMapper.getSubmissionByAssignmentAndStudent(assignment.getId(), studentId);
            assignment.setSubmission(submission);
        }
        return assignments;
    }

    @Override
    public ResponseEntity<Result> getAssignmentDownloadUrl(Long assignmentId) {
        try {
            // Retrieve the material details by its ID
            Assignment assignment = assignmentMapper.selectById(assignmentId);

            // Generate the download URL for the material
            String downloadUrl = fileServiceForTeachingModule.generateDownloadUrl(assignment.getUrl());

            // Return the download URL in the response
            return ResponseEntity.ok(Result.success(downloadUrl));
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(500, "Failed to generate download URL"));
        }
    }

    @Override
    public Result submitAssignment(Long studentId, Long assignmentId, MultipartFile multipartFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiniuUtilsForTeaching.upload(multipartFile, fileName);
        if (upload) {
            // 插入记录
            Submission submission = new Submission();
            submission.setStatus(TeachingModuleConfig.HAVE_SUBMITTED);
            submission.setAssignmentId(assignmentId);
            submission.setStudentId(studentId);
            submission.setSubmissionUrl(fileName);
            submission.setTime(LocalDateTime.now());
            submission.setFileName(originalFilename);
            submission.setStudentName(
                    objectMapper.convertValue(sysUserClient.queryInfo(studentId).getData(), SysUser.class).getNickname()
            );
            submissionMapper.insert(submission);
            return Result.success(fileName);
        }
        return Result.fail(500, "上传失败");
    }

    @Override
    public List<CourseVo> getJoinedCourses(Long studentId) {
        return courseMapper.getJoinedCoursesByStudentId(studentId);
    }

    @Override
    public Result applyToBeAssistant(Long studentId, Long courseId) {
        Assistant existingassistant = assistantMapper.selectByStudentIdAndCourseId(studentId, courseId);
        if (existingassistant != null) {
            switch (existingassistant.getStatus()) {
                case TeachingModuleConfig.APPLYING_TO_BE_ASSISTANT:
                    return Result.fail(404, "申请成为助教中~");
                case TeachingModuleConfig.ACCEPTED_TO_BE_ASSISTANT:
                    return Result.fail(404, "你已经是该门课的助教了！");
                case TeachingModuleConfig.DENIED_TO_BE_ASSISTANT:
                    return Result.fail(404, "抱歉，你被老师拒绝了");
                default:
                    // 可以在这里处理其他未知的status情况
                    return Result.fail(404, "未知状态");
            }
        }
        // 创建新记录
        Assistant assistant = new Assistant();
        assistant.setStudentId(studentId);
        assistant.setCourseId(courseId);
        assistant.setStatus(TeachingModuleConfig.APPLYING_TO_BE_ASSISTANT);
        // 插入记录到Assistant表
        int rowsAffected = assistantMapper.insert(assistant);
        if (rowsAffected > 0) {
            return Result.success("申请成为助教成功");
        } else {
            return Result.fail(404, "申请失败，请重试");
        }
    }

    /**
     * 工厂方法（使用同一个 queryWrapper 会相互干扰）构建跨表查询，注意主表被重命名为 t
     *
     * @param studentId 学生id
     * @return queryWrapper
     */
    private MPJQueryWrapper<Course> queryWrapperFactory(Long studentId) {
        return new MPJQueryWrapper<Course>()
                .select("t.id as id", "t.enrollment as enrollment", "t.capacity as capacity",
                        "t.dept_name as dept_name", "t.week_begin as week_begin", "t.week_end as week_end",
                        "t.day as day", "t.section_begin as section_begin",
                        "t.section_end as section_end", "t.room as room",
                        "t.teacher_name as teacherName")
                .select("curriculum.name as curriculumName", "curriculum.credit as credit", "curriculum.introduction as introduction")
                .leftJoin("curriculum on t.curriculum_id = curriculum.id")
                // 排除选过的课程组
                .notExists("select * from sc t1 left join course t2 on t1.course_id = t2.id " +
                        "where t2.curriculum_id = t.curriculum_id " +
                        "and t1.student_id = " + studentId)
                .notExists("select * from assistant t3 where t.id = t3.course_id and t3.student_id = " + studentId);
    }

    @Override
    public Result listCoursePage(String token, StudentLearningPageParam studentLearningPageParam) {
        Long studentId = loginService.checkToken(token).getId();
        MPJQueryWrapper<Course> queryWrapper = queryWrapperFactory(studentId);
        AdminPageParam adminPageParam = studentLearningPageParam.getAdminPageParam();
        Boolean excludeFull = studentLearningPageParam.getExcludeFull();
        Boolean excludeConflict = studentLearningPageParam.getExcludeConflict();
        List<FilterData> filterDataList = adminPageParam.getFilterDataList();
        if (FilterData.injectFilter(queryWrapper, filterDataList, StudentLearningServiceImpl::mapFunction)) {
            if (excludeFull) queryWrapper.lt("t.enrollment", "t.capacity");
            if (excludeConflict) {
                queryWrapper.notExists("select * from sc t4 left join course t5 on t4.course_id = t5.id " +
                        "where (t5.day = t.day and not (t5.section_end < t.section_begin or t5.section_begin > t.section_end)) " +
                        "and t4.student_id = " + studentId);
            }
            StudentLearningCourseVo studentLearningCourseVo = new StudentLearningCourseVo();
            Page<StudentLearningCourseInfoVo> page = courseMapper.selectJoinPage(new Page<>(adminPageParam.getPage(), adminPageParam.getPageSize()), StudentLearningCourseInfoVo.class, queryWrapper);
            studentLearningCourseVo.setStudentLearningCourseInfoVoList(page.getRecords());
            studentLearningCourseVo.setStudentLearningCourseInfoVoCount(page.getTotal());
            return Result.success(studentLearningCourseVo);
        } else {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public List<Material> getMaterialByArticleId(Long articleId) {
        return articleMaterialMapper.findMaterialsByArticleId(articleId);
    }

    @Override
    public ResponseEntity<Result> downloadMaterialForArticle(Long materialId) {
        try {
            // Retrieve the material details by its ID
            ArticleMaterial material = articleMaterialMapper.selectById(materialId);

            // Generate the download URL for the material
            String downloadUrl = fileServiceForTeachingModule.generateDownloadUrlForArticle(material.getUrl());

            // Return the download URL in the response
            return ResponseEntity.ok(Result.success(downloadUrl));
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(500, "Failed to generate download URL"));
        }
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
                default:
                    filterData.setName("t." + filterData.getName());
                    break;
            }
        }
        return filterDataList;
    }
}
