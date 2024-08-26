package com.example.usercenterapi.dao.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.vo.params.AdminPageParam;
import lombok.Data;

import java.util.List;
@Data
public class TryAdminPageParam {
    private AdminPageParam adminPageParam;
    private QueryWrapper<SysUser> queryWrapper;
}
