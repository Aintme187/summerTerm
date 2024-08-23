package com.example.manageapi.service;

import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

//事务注解
@Transactional
public interface LoginService {

    SysUser checkToken(String token);
}
