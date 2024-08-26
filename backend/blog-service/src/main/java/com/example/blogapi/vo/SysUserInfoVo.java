package com.example.blogapi.vo;

import lombok.Data;

@Data
public class SysUserInfoVo {
    private String account;

    private String password;

    private String nickname;

    private String email;

    private String mobilePhoneNumber;

    private String permission;

    private String deptName;

    private String signature;
}
