package com.example.usercenterapi.vo;

import lombok.Data;

@Data
public class SysUserInfoVo {
    private String account;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String mobilePhoneNumber;

    private String permission;

    private String deptName;

    private String signature;
}
