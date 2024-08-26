package com.example.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenterapi.dao.dto.TryAdminPageParam;
import com.example.usercenterapi.dao.mapper.SysUserMapper;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.service.LoginService;
import com.example.usercenterapi.service.SysUserService;
import com.example.usercenterapi.vo.ErrorCode;
import com.example.usercenterapi.vo.LoginUserVo;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.UserVo;
import com.example.usercenterapi.vo.AdminSysUserVo;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.FilterData;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public SysUser selectById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public Result selectPage(AdminPageParam adminPageParam){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        List<FilterData> filterDataList = adminPageParam.getFilterDataList();
        if (FilterData.injectFilter(queryWrapper, filterDataList)) {
            AdminSysUserVo adminSysUserVo = new AdminSysUserVo();
            try {
                Page<SysUser> sysUserPage = sysUserMapper.selectPage(new Page<>(adminPageParam.getPage(), adminPageParam.getPageSize()), queryWrapper);
                adminSysUserVo.setAdminSysUserInfoList(sysUsers2adminSysUserInfos(sysUserPage.getRecords()));
                adminSysUserVo.setAdminSysUserCount(sysUserPage.getTotal());
                return Result.success(adminSysUserVo);
            } catch (Exception e) {
                return Result.fail(ErrorCode.DATA_ERROR);
            }
        } else {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
    }

    private List<AdminSysUserVo.AdminSysUserInfo> sysUsers2adminSysUserInfos(List<SysUser> records) {
        List<AdminSysUserVo.AdminSysUserInfo> SysUserInfoList = new ArrayList<>();
        for (SysUser record : records) {
            SysUserInfoList.add(sysUser2adminSysUserInfo(record));
        }
        return SysUserInfoList;
    }

    private AdminSysUserVo.AdminSysUserInfo sysUser2adminSysUserInfo(SysUser sysUser) {
        AdminSysUserVo.AdminSysUserInfo sysUserInfo = new AdminSysUserVo.AdminSysUserInfo();
        BeanUtils.copyProperties(sysUser, sysUserInfo);
        //转换 data 类型数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysUserInfo.setCreateDate(simpleDateFormat.format(sysUser.getCreateDate()));
        return sysUserInfo;
    }

    @Override
    public void insert(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void updateById(SysUser sysUser) {
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public Result deleteById(Long id) {
        try {
            sysUserMapper.deleteById(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public List<SysUser> selectBatchIds(List<Long> ids) {
        return sysUserMapper.selectBatchIds(ids);
    }

    @Override
    public Result deleteBatchIds(List<Long> ids) {
        try {
            sysUserMapper.deleteBatchIds(ids);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public void update(SysUser sysUser, LambdaUpdateWrapper<SysUser> queryWrapper) {
        sysUserMapper.update(sysUser, queryWrapper);
    }
}
