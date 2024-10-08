package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String account;

    private String avatar;

    private Date createDate;

    private String email;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String signature;

    private String permission;

    private String deptName;
}
