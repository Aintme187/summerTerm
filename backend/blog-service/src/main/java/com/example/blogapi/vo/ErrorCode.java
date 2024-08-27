package com.example.blogapi.vo;

public enum ErrorCode {
    IS_SENTINEL(10107,"被限流了！！！！！！！！！！！！！"),
    PARAMS_ERROR(10001, "参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002, "用户名或密码不存在"),
    TOKEN_ERROR(10003, "token不合法"),
    ACCOUNT_EXIST(10004, "账号已注册"),
    NO_USER(10005, "用户不存在"),
    ILLEGAL_ACCOUNT(10006, "不合法的账号，账号由5-20位数字字母下划线组成"),
    ILLEGAL_NICKNAME(10007, "不合法的昵称，昵称不能为空且不能包含\"!@#$%^&*()<>?,./\"等特殊字符"),
    ILLEGAL_PASSWORD(10008, "不合法的密码，密码由5-20位数字字母下划线或@#$%组成"),
    ILLEGAL_EMAIL(10009, "不合法的邮箱地址"),
    ILLEGAL_PHONE(10010, "不合法的电话号码"),
    ILLEGAL_PERMISSION(10011, "不合法的身份，身份只能是 'administer','teacher','student'"),
    // bug here
    ILLEGAL_DEPTNAME(10011, "不合法的系名称"),
    ILLEGAL_SIGNATURE(10011, "不合法的个性签名，个性签名不能超过200字"),

    NOT_A_TEACHER(11101, "不是教师id"),

    NO_PERMISSION(70001, "无访问权限"),

    SESSION_TIME_OUT(90001, "会话超时"),
    NO_LOGIN(90002, "未登录"),

    NO_TEACHER(30001, "老师不存在"),
    NO_CURRICULUM(30002, "课程不存在"),
    NO_COURSE(30003, "教学班不存在"),
    CAPACITY_LT_ENROLLMENT(30004, "容量不能小于选课人数，当前最大选课人数为："),
    ILLEGAL_WEEK(30005, "不合法的周次"),
    ILLEGAL_SECTION(30005, "不合法的节次"),
    ILLEGAL_WEEKDAY(30005, "不合法的周几"),

    DATA_ERROR(44444, "数据库错误，请联系管理员"),

    ILLEGAL_CURRICULUM_NAME(50001, "不合法的课程名，课程名不能超过30个字"),
    ILLEGAL_CREDIT(50002, "不合法的学分，学分是大于0的小数"),
    ILLEGAL_INTRODUCTION(50003, "不合法的课程简介，课程简介不能超过200字"),
    IS_LIKE(10106, "已经被点赞");




    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
