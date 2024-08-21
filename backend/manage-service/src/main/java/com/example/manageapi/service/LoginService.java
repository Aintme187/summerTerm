package com.example.manageapi.service;

import com.example.manageapi.dao.pojo.SysUser;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.LoginParam;
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
