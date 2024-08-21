package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.UserVo;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    SysUser getUserInfo(String account, String password);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long id);

    Result updateUser(SysUser sysUser);

    Result getInfoById(Long id);

    Result updateAvatar(Long id,String url);
}
