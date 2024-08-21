package com.example.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.usercenterapi.dao.mapper.SysUserMapper;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.service.LoginService;
import com.example.usercenterapi.service.SysUserService;
import com.example.usercenterapi.vo.ErrorCode;
import com.example.usercenterapi.vo.LoginUserVo;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("默认作者");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        /*
        eq是查询条件
        select是查询哪些字段
         */
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname, SysUser::getPermission);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser getUserInfo(String account, String password) {
        /*
        eq是查询条件
        select是查询哪些字段
         */
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性校验 是否为空 解析是否成功 redis是否存在
         * 2.校验失败返回错误
         * 3.校验成功返回结果 LoginUserVo
         */

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setPermission(sysUser.getPermission());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("默认作者");
        }
        UserVo userVo = new UserVo();
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());
        userVo.setId(sysUser.getId());
        return userVo;
    }

    public Result updateUser(SysUser sysUser) {//sysUser中存放的是前端传回的用户修改信息

        SysUser existingUser = sysUserMapper.selectById(sysUser.getId());

//        if (sysUser.equals(existingUser)) {//更新信息若与原先相同则报错
//            return Result.fail(ErrorCode.USER_INFO_NOT_CHANGED.getCode(), ErrorCode.USER_INFO_NOT_CHANGED.getMsg());
//        }
//        if (sysUser.getEmail() != null && !sysUser.getEmail().equals(existingUser.getEmail()) ) {
//            sysUser.setEmail(sysUser.getEmail());
//        }
//        if (sysUser.getMobilePhoneNumber() != null && !sysUser.getMobilePhoneNumber().equals(existingUser.getMobilePhoneNumber())) {
//            sysUser.setMobilePhoneNumber(sysUser.getMobilePhoneNumber());
//        }
//        if (sysUser.getPassword() != null && !sysUser.getPassword().equals(existingUser.getPassword())) {
//            String updatedPassword = DigestUtils.md5Hex(sysUser.getPassword() + existingUser.getSalt());
//            sysUser.setPassword(updatedPassword);
//        }
        if(sysUser.getPassword()!=null) {
            sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword() + "salt"));
        }
        sysUserMapper.updateById(sysUser);
        return Result.success(sysUser);
    }

    @Override
    public Result getInfoById(Long id) {
        return Result.success(sysUserMapper.selectById(id));
    }

    @Override
    public Result updateAvatar(Long id, String url) {
        SysUser user = sysUserMapper.selectById(id);
        user.setAvatar(url);
        sysUserMapper.updateById(user);
        return Result.success(user);
    }
}
