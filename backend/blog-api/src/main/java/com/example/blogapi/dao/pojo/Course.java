package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Keith
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long curriculumId;

    private Long enrollment;

    private Long capacity;

    private Long teacherId;

    private String deptName;

    private Long weekBegin;

    private Long weekEnd;

    private Long day;

    private Long sectionBegin;

    private Long sectionEnd;

    private String room;

    private Long categoryId;
}
