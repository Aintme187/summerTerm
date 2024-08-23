package com.example.teachapi.dao.dto;

import lombok.Data;

/**
 * @author ddddm
 **/
@Data
public class AdminCourseInfoVo {

        private Long id;

        private Long curriculumId;

        private String curriculumName;

        private Double credit;

        private String introduction;

        private Long enrollment;

        private Long capacity;

        private Long teacherId;

        private String teacherName;

        private String deptName;

        private Long weekBegin;

        private Long weekEnd;

        private Long day;

        private Long sectionBegin;

        private Long sectionEnd;

        private String room;
}
