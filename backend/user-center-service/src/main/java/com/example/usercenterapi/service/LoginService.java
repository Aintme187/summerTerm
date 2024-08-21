package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

//事务注解
@Transactional
public interface LoginService {

    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    Result register(LoginParam loginParam);
}
