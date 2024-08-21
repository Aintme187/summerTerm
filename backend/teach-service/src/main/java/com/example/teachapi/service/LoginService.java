package com.example.teachapi.service;

import com.example.teachapi.dao.pojo.SysUser;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.LoginParam;
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
