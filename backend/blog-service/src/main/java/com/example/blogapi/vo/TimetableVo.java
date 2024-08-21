package com.example.blogapi.vo;

import lombok.Data;

/**
 * @author Keith
 */
@Data
public class TimetableVo {

    // 返回星期几
    private Long day;

    // 课程名
    private String name;

    //拼凑的内容：开始周次 + 结课周次 + 上课地点 + 任课教师
    private String content;

    // 开始节数
    private Long sectionBegin;

    // 结束节数
    private Long sectionEnd;
}
