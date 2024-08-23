package com.example.teachapi.service.impl;

import com.example.teachapi.client.sysUserClient;
import com.example.teachapi.config.TeachingModuleConfig;
import com.example.teachapi.dao.dto.SysUser;
import com.example.teachapi.dao.mapper.*;
import com.example.teachapi.dao.pojo.*;
import com.example.teachapi.service.FileServiceForTeachingModule;
import com.example.teachapi.service.TeacherTeachingService;
import com.example.teachapi.utils.QiniuUtilsForTeaching;
import com.example.teachapi.utils.StringUtilsForTeaching;
import com.example.teachapi.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class TeacherTeachingServiceImpl implements TeacherTeachingService {
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private FileServiceForTeachingModule fileServiceForTeachingModule;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private SubmissionMapper submissionMapper;
    @Autowired
    private SCMapper scMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private AssistantMapper assistantMapper;
    @Autowired
    private CurriculumMapper curriculumMapper;
    @Autowired
    private QiniuUtilsForTeaching qiniuUtilsForTeaching;
    @Autowired
    private sysUserClient sysUserClient;

    @Override
    public List<TimetableVo> getCourseTable(Long teacherId) {
        List<Course> teachingCourses = courseMapper.getCoursesByTeacherId(teacherId);
        List<TimetableVo> timetable = new ArrayList<>();

        for (Course course : teachingCourses) {
            Curriculum curriculum = curriculumMapper.selectCurriculumById(course.getCurriculumId());

            TimetableVo vo = new TimetableVo();
            vo.setDay(course.getDay());
            vo.setName(curriculum.getName());
            vo.setContent(String.format(
                    "%d-%d周 %s %s",
                    course.getWeekBegin(),
                    course.getWeekEnd(),
                    course.getRoom(),
                    //sysUserMapper.getNameById(teacherId)
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
    public List<CourseVo> getTeachingCourses(Long teacherId){
        return courseMapper.getCourseVoByTeacherId(teacherId);
    }

    @Override
    public List<ScApplicationVo> showScApplication(Long teacherId){
        List<Course> teachingCourses = courseMapper.getCoursesByTeacherId(teacherId);
        List<ScApplicationVo> scApplications = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Course course : teachingCourses) {
            List<Sc> scApplicationList = scMapper.selectApplicationByCourseId(course.getId());
            Curriculum curriculum = curriculumMapper.selectCurriculumById(course.getCurriculumId());
            for (Sc sc : scApplicationList) {
                ScApplicationVo vo = new ScApplicationVo();
                vo.setScId(sc.getId());
                vo.setStudentId(sc.getStudentId());
                vo.setStudentName(
                        //sysUserMapper.getNameById(sc.getStudentId())
                        objectMapper.convertValue(sysUserClient.queryInfo(sc.getStudentId()).getData(), SysUser.class).getNickname()
                );
                vo.setCourseId(course.getId());
                vo.setCourseName(curriculum.getName());

                scApplications.add(vo);
            }
        }
        return scApplications;
    }

    @Override
    public List<AssistantApplicationVo> showAssistantApplication(Long teacherId){
        List<Course> teachingCourses = courseMapper.getCoursesByTeacherId(teacherId);
        List<AssistantApplicationVo> assistantApplications = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Course course : teachingCourses) {
            List<Assistant> AssitantApplicationList = assistantMapper.selectApplicationByCourseId(course.getId());
            Curriculum curriculum = curriculumMapper.selectCurriculumById(course.getCurriculumId());
            for (Assistant assistant : AssitantApplicationList) {
                AssistantApplicationVo vo = new AssistantApplicationVo();
                vo.setAssistantId(assistant.getId());
                vo.setStudentId(assistant.getStudentId());
                vo.setStudentName(
                        //sysUserMapper.getNameById(assistant.getStudentId())
                        objectMapper.convertValue(sysUserClient.queryInfo(assistant.getStudentId()).getData(), SysUser.class).getNickname()
                );
                vo.setCourseId(course.getId());
                vo.setCourseName(curriculum.getName());

                assistantApplications.add(vo);
            }
        }
        return assistantApplications;
    }

    @Override
    public Result submitMaterial(Long courseId, MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiniuUtilsForTeaching.upload(multipartFile, fileName);
        if(upload){
            // 插入记录
            Material material = new Material();
            material.setTime(LocalDateTime.now());
            material.setCourseId(courseId);
            material.setUrl(fileName);
            material.setName(originalFilename);
            material.setSize(StringUtilsForTeaching.formatSize(multipartFile.getSize()));
            materialMapper.insert(material);
            return Result.success(fileName);
        }
        return Result.fail(500, "上传失败");
    }

    @Override
    public List<AssignmentVo> getAssignments(Long teacherId) {
        List<Course> teachingCourses = courseMapper.getCoursesByTeacherId(teacherId);
        List<AssignmentVo> assignments = new ArrayList<>();

        for (Course course : teachingCourses) {
            List<AssignmentVo> addVo = new ArrayList<>();
            if ((addVo = assignmentMapper.getAssignmentsByCourseId(course.getId())) != null) {
                assignments.addAll(addVo);
            }
        }

        return assignments;
    }

    @Override
    public List<AssignmentVo> getCourseAssignments(Long courseId) {
        return assignmentMapper.getAssignmentsByCourseId(courseId);
    }

    @Override
    public Result releaseAssignment(String name, String description, LocalDateTime beginTime, LocalDateTime endTime, Long courseId, MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiniuUtilsForTeaching.upload(multipartFile, fileName);
        if(upload){
            // 插入记录
            Assignment assignment = new Assignment();
            assignment.setName(name);
            assignment.setDescription(description);
            assignment.setBeginTime(beginTime);
            assignment.setEndTime(endTime);
            assignment.setUrl(fileName);
            assignment.setCourseId(courseId);
            assignment.setFileName(originalFilename);
            assignmentMapper.insert(assignment);
            return Result.success(fileName);
        }
        return Result.fail(500, "上传失败");
    }

    @Override
    public List<SubmissionVo> showSubmissions(Long assignmentId) {
        return submissionMapper.selectByAssignmentId(assignmentId);
    }

    @Override
    public ResponseEntity<Result> downloadSubmission(Long submissionId) {
        try {
            // Retrieve the material details by its ID
            Submission submission = submissionMapper.selectById(submissionId);

            // Generate the download URL for the material
            String downloadUrl = fileServiceForTeachingModule.generateDownloadUrl(submission.getSubmissionUrl());

            // Return the download URL in the response
            return ResponseEntity.ok(Result.success(downloadUrl));
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(500, "Failed to generate download URL"));
        }
    }

    @Override
    public Result correctSubmission(Long submissionId, Long score, MultipartFile feedbackFile, String comment) {
        String originalFilename = feedbackFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiniuUtilsForTeaching.upload(feedbackFile, fileName);
        Submission submission = submissionMapper.selectById(submissionId);

        if(upload) {
            submission.setScore(score);
            submission.setFeedbackUrl(fileName);
            submission.setComment(comment);
            submission.setStatus(TeachingModuleConfig.HAVE_CORRECTED);
            submissionMapper.updateById(submission);
        }
        return Result.fail(500, "反馈文件上传失败");
    }

    @Override
    public Result modifyCourseParticipants(Long courseId, Long studentId, boolean operation) {
        Sc existingsc = scMapper.selectByStudentIdAndCourseId(studentId, courseId);

        // 增加或同意
        if (operation) {
            if (existingsc == null){    // 如果没有申请加入课程
                Sc sc = new Sc();
                sc.setStudentId(studentId);
                sc.setCourseId(courseId);
                sc.setStatus(TeachingModuleConfig.ACCEPTED_TO_JOIN_COURSE);
                sc.setScore(0);
                // 插入记录到SC表
                int rowsAffected = scMapper.insert(sc);
                if (rowsAffected > 0) {
                    return Result.success("加入课程成功");
                } else {
                    return Result.fail(404, "加入失败，请重试");
                }
            } else if (existingsc.getStatus() == TeachingModuleConfig.JOINING_COURSE || existingsc.getStatus() == TeachingModuleConfig.DENIED_TO_JOIN_COURSE) { // 如果正在审批或已拒绝
                Course course = courseMapper.selectById(courseId);
                if (course.getEnrollment() >= course.getCapacity()){
                    return Result.fail(404, "人数已满，添加失败");
                }
                existingsc.setStatus(TeachingModuleConfig.ACCEPTED_TO_JOIN_COURSE);
                scMapper.updateById(existingsc);
                return Result.success("已同意加入");
            } else {
                // 可以在这里处理其他未知的status情况
                return Result.fail(404, "未知状态");
            }
        }else { // 删除或拒绝
            if (existingsc == null) {
                return Result.fail(404, "该课程没有该学生");
            }else if (existingsc.getStatus() == TeachingModuleConfig.ACCEPTED_TO_JOIN_COURSE) {   // 已加入课程
                int result = scMapper.deleteById(scMapper.selectByStudentIdAndCourseId(studentId, courseId).getId());
                if (result > 0) {
                    Course course = courseMapper.selectById(courseId);
                    return Result.success("删除学生成功");
                } else {
                    return Result.fail(404, "删除失败");
                }
            } else if (existingsc.getStatus() == TeachingModuleConfig.JOINING_COURSE) { // 如果正在审批
                existingsc.setStatus(TeachingModuleConfig.DENIED_TO_BE_ASSISTANT);
                scMapper.updateById(existingsc);
                return Result.success("已拒绝申请");
            } else{
                // 可以在这里处理其他未知的status情况
                return Result.fail(404, "未知状态");
            }
        }
    }

    @Override
    public Result modifyCourseAssistants(Long courseId, Long studentId, boolean operation) {
        Assistant existingassistant = assistantMapper.selectByStudentIdAndCourseId(studentId, courseId);

        // 增加或同意
        if (operation) {
            if (existingassistant == null){    // 如果没有申请成为助教
                Assistant assistant = new Assistant();
                assistant.setStudentId(studentId);
                assistant.setCourseId(courseId);
                assistant.setStatus(TeachingModuleConfig.ACCEPTED_TO_BE_ASSISTANT);
                // 插入记录到Assistant表
                int rowsAffected = assistantMapper.insert(assistant);
                if (rowsAffected > 0) {
                    return Result.success("设置助教成功");
                } else {
                    return Result.fail(404, "设置助教失败，请重试");
                }
            } else if (existingassistant.getStatus() == TeachingModuleConfig.APPLYING_TO_BE_ASSISTANT || existingassistant.getStatus() == TeachingModuleConfig.DENIED_TO_BE_ASSISTANT) { // 如果正在审批或已拒绝
                existingassistant.setStatus(TeachingModuleConfig.ACCEPTED_TO_BE_ASSISTANT);
                assistantMapper.updateById(existingassistant);
                return Result.success("已同意加入");
            } else {
                // 可以在这里处理其他未知的status情况
                return Result.fail(404, "未知状态");
            }
        }else { // 删除或拒绝
            if (existingassistant == null) {
                return Result.fail(404, "该课程没有该助教");
            }else if (existingassistant.getStatus() == TeachingModuleConfig.ACCEPTED_TO_BE_ASSISTANT) {   // 已成为助教
                int result = assistantMapper.deleteByStudentIdAndCourseId(studentId, courseId);
                if (result > 0) {
                    return Result.success("删除助教成功");
                } else {
                    return Result.fail(404, "删除失败");
                }
            } else if (existingassistant.getStatus() == TeachingModuleConfig.APPLYING_TO_BE_ASSISTANT) { // 如果正在审批
                existingassistant.setStatus(TeachingModuleConfig.DENIED_TO_BE_ASSISTANT);
                assistantMapper.updateById(existingassistant);
                return Result.success("已拒绝申请");
            } else{
                // 可以在这里处理其他未知的status情况
                return Result.fail(404, "未知状态");
            }
        }
    }
}