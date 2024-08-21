package com.example.usercenterapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.service.LoginService;
import com.example.usercenterapi.service.SysUserService;
import com.example.usercenterapi.utils.JWTUtils;
import com.example.usercenterapi.vo.ErrorCode;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // bug here
    private static final String salt = "salt";

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法，根据用户名和密码去user表中查询是否存在
         * 2.如果存在利用JWT生成token返回
         * 3.讲token放入redis中 token:user信息
         * 4.登录时先认证token，再认证redis中是否存在
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        password = DigestUtils.md5Hex(password + salt);
        //System.out.println("password" + password);

        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在 存在返回已经注册
         * 3.不存在则注册
         * 4.生成token，存入redis并返回
         * 5.加上事务，如果发送问题需要回滚，防止异常数据添加进数据库当中
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + salt));
        sysUser.setCreateDate(new Date(System.currentTimeMillis()));
        sysUser.setAvatar("http://seicj6zi6.hb-bkt.clouddn.com/%E8%BD%AF%E4%BB%B6%E5%AD%A6%E9%99%A2.jpg");
        sysUser.setSalt("salt");
        sysUser.setSignature("");
        sysUser.setEmail("");
        /*
         * bug here
         * 通过注册接口注册的只能是学生
         */
        sysUser.setPermission("student");
        sysUser.setDeptName("");
        sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }
}
