package com.example.teachapi.service;

import com.example.teachapi.dao.dto.SysUser;
import org.springframework.transaction.annotation.Transactional;

//事务注解
@Transactional
public interface LoginService {

    SysUser checkToken(String token);
}
