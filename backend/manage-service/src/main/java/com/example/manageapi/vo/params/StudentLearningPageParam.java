package com.example.manageapi.vo.params;

import lombok.Data;

/**
 * 学生教学分页查询参数
 */
@Data
public class StudentLearningPageParam {
    private AdminPageParam adminPageParam;
    // 排除选满的课程
    private Boolean excludeFull;
    // 排除冲突的课程
    private Boolean excludeConflict;
}
