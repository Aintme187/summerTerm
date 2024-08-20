package com.example.blogapi.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class LoginUserVo {

    private Long id;
    private String account;
    private String nickname;
    private String avatar;
    private String permission;
}
