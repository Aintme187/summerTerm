package com.example.blogapi.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVo {



    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String account;

    private String avatar;

    private String createDate;

    private String email;

    private Date lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status; // no use

    private String permission;
}
