package com.example.usercenterapi.service;

import com.example.usercenterapi.vo.ErrorCode;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.LoginParam;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void loginPositive() {
        LoginParam loginParam=new LoginParam();
        loginParam.setAccount("admin");
        loginParam.setPassword("admin");
        Result result=loginService.login(loginParam);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertNotNull(result.getData());
    }

    @Test
    public void loginNegative() {
        LoginParam loginParam=new LoginParam();
        loginParam.setAccount("");
        loginParam.setPassword("");
        Result result=loginService.login(loginParam);
        assertEquals(ErrorCode.PARAMS_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCode.PARAMS_ERROR.getMsg(), result.getMsg());
    }

    @Test
    public void registerPositive() {
        LoginParam loginParam=new LoginParam();
        loginParam.setAccount("junittest");
        loginParam.setPassword("junittest");
        loginParam.setNickname("单元测试");
        Result result=loginService.register(loginParam);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertNotNull(result.getData());
    }

    @Test
    public void registerNegative() {
        LoginParam loginParam=new LoginParam();
        loginParam.setAccount("");
        loginParam.setPassword("");
        Result result=loginService.register(loginParam);
        assertEquals(ErrorCode.PARAMS_ERROR.getCode(), result.getCode());
        assertEquals(ErrorCode.PARAMS_ERROR.getMsg(), result.getMsg());
    }
}