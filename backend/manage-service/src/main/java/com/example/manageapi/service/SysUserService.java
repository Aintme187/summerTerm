package com.example.manageapi.service;

import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.UserVo;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface SysUserService {

    SysUser findUserByAccount(String account);
}
