package com.example.usercenterapi.service;

import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.vo.ErrorCode;
import com.example.usercenterapi.vo.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SysUserServiceTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    void findUserPositive() {
        SysUser sysUser=sysUserService.findUser("admin","3042d7230c565a47d72a21d39759cb3d");
        Assertions.assertNotNull(sysUser);
    }

    @Test
    void findUserNegative() {
        SysUser sysUser=sysUserService.findUser("","");
        Assertions.assertNull(sysUser);
    }

    @Test
    void findUserByAccountPositive() {
        SysUser sysUser=sysUserService.findUserByAccount("admin");
        Assertions.assertNotNull(sysUser);
    }

    @Test
    void findUserByAccountNegative() {
        SysUser sysUser=sysUserService.findUserByAccount("");
        Assertions.assertNull(sysUser);
    }

    @Test
    void updateUserPositive() {
        SysUser sysUser=new SysUser();
        sysUser.setAccount("admin");
        sysUser.setId(Long.valueOf(1));
        sysUser.setPassword("admin");
        sysUser.setAvatar("http://sio7mkwqx.hb-bkt.clouddn.com/1c2a47f2-0e65-472b-a859-6e74416849f3.png");
        sysUser.setNickname("李四");
        sysUser.setPermission("student");
        Result result=sysUserService.updateUser(sysUser);
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    void updateUserNegative() {
        SysUser sysUser=new SysUser();
        Result result=sysUserService.updateUser(sysUser);
        assertEquals(ErrorCode.PARAMS_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCode.PARAMS_ERROR.getMsg(), result.getMsg());
    }

    @Test
    void updateAvatarPositive() {
        Result result=sysUserService.updateAvatar(Long.valueOf(1),"http://sio7mkwqx.hb-bkt.clouddn.com/1c2a47f2-0e65-472b-a859-6e74416849f3.png");
        Assertions.assertNotNull(result.getData());
    }

    @Test
    void updateAvatarNegative() {
        Result result=sysUserService.updateAvatar(Long.valueOf(0),"http://sio7mkwqx.hb-bkt.clouddn.com/1c2a47f2-0e65-472b-a859-6e74416849f3.png");
        assertEquals(ErrorCode.PARAMS_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCode.PARAMS_ERROR.getMsg(), result.getMsg());
    }

    @Test
    void selectByIdPositive() {
        SysUser sysUser=sysUserService.selectById(Long.valueOf(1));
        Assertions.assertNotNull(sysUser);
    }

    @Test
    void selectByIdNegative() {
        SysUser sysUser=sysUserService.selectById(Long.valueOf(0));
        Assertions.assertNull(sysUser);
    }
}