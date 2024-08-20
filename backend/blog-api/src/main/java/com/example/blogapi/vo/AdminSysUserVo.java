package com.example.blogapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class AdminSysUserVo {
    List<AdminSysUserInfo> adminSysUserInfoList;
    Long AdminSysUserCount;

    /**
     * 在列表页面显示的内容
     */
    @Data
    public static class AdminSysUserInfo {
        private Long id;

        private String account;

        private String nickname;

        private String email;

        private String mobilePhoneNumber;

        private String permission;

        private String deptName;

        private String signature;

        private String createDate;
    }
}


