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
import com.example.usercenterapi.vo.SysUserInfoVo;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.FilterData;
import com.example.usercenterapi.vo.params.BatchUpdateSysUsersParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;

    private final String salt = "salt";

    private final String[] deptNames = {
            "安全科学学院", "材料学院", "电机工程与应用电子技术系", "法学院", "工程物理系", "公共管理学院",
            "国家卓越工程师学院", "航天航空学院", "核能与新能源技术研究院", "化学工程系", "环境学院", "计算机学院",
            "机械工程学院", "能源与动力工程系", "车辆与运载学院", "建筑学院", "教育研究院", "经济管理学院",
            "理学院", "马克思主义学院", "美术学院", "人文学院", "软件工程学院", "社会科学学院",
            "生命科学学院", "生物医学交叉研究院", "土木水利学院", "新闻与传播学院", "信息科学技术学院", "药学院",
            "医学院"
    };

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

    public Result updateUser(SysUser sysUser) {//sysUser中存放的是前端传回的用户修改信息
        if(sysUser.getId()==null){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        if(sysUser.getPassword()!=null) {
            sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword() + "salt"));
        }
        sysUserMapper.updateById(sysUser);
        return Result.success(sysUser);
    }



    @Override
    public Result updateAvatar(Long id, String url) {
        if(id==0){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
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
    public Result update(BatchUpdateSysUsersParam batchUpdateSysUsersParam) {
        List<Long> ids = batchUpdateSysUsersParam.getIds();
        SysUserInfoVo sysUserInfoVo = batchUpdateSysUsersParam.getSysUserInfoVo();
        /*
        因为可能更新为空字符串 ""
        所以指定 null 为不更新
         */
        if (sysUserInfoVo.getPassword() != null) {
            sysUserInfoVo.setPassword(DigestUtils.md5Hex(sysUserInfoVo.getPassword()));
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserInfoVo, sysUser);
        ErrorCode errorCode = errorInSysUser(sysUser);
        if (errorCode != null) return Result.fail(errorCode);
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(SysUser::getId, ids);
        try {
            sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword() + salt));
            sysUser.setPermission(null);
            sysUserMapper.update(sysUser, updateWrapper);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    /**
     * 检查 sysUserInfoVo 中的错误
     * 会忽略掉 null 字段
     *
     * @param sysUser 待检查的 sysUserInfoVo
     * @return ErrorCode | null(没有错误)
     */
    private ErrorCode errorInSysUser(SysUser sysUser) {
        String legalAccount = "^\\w{5,20}$";
        String legalPassword = "^[\\w@#$%]{5,20}$";
        String legalNickName = "^[^!@#$%^&*()<>?,./]+$";
        String legalEmail = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String legalPhone = "^1[3578]\\d{9}$";
        String account = sysUser.getAccount();
        String password = sysUser.getPassword();
        String nickname = sysUser.getNickname();
        String email = sysUser.getEmail();
        String mobilePhoneNumber = sysUser.getMobilePhoneNumber();
        String permission = sysUser.getPermission();
        String signature = sysUser.getSignature();
        String deptName = sysUser.getDeptName();

        if (account != null && !account.matches(legalAccount)) {
            return ErrorCode.ILLEGAL_ACCOUNT;
        } else if (password != null && !password.matches(legalPassword)) {
            return ErrorCode.ILLEGAL_PASSWORD;
        } else if (nickname != null && !nickname.matches(legalNickName)) {
            return ErrorCode.ILLEGAL_NICKNAME;
        } else if (email != null && !email.isEmpty() && !email.matches(legalEmail)) {
            return ErrorCode.ILLEGAL_EMAIL;
        } else if (mobilePhoneNumber != null && !mobilePhoneNumber.isEmpty() && !mobilePhoneNumber.matches(legalPhone)) {
            return ErrorCode.ILLEGAL_PHONE;
        } else if (permission != null && !(permission.equals("administer") || permission.equals("teacher") || permission.equals("student"))) {
            return ErrorCode.ILLEGAL_PERMISSION;
        } else if (signature != null && signature.length() > 200) {
            return ErrorCode.ILLEGAL_SIGNATURE;
        } else if (deptName != null && !Arrays.asList(deptNames).contains(deptName)) {
            return ErrorCode.ILLEGAL_DEPTNAME;
        }
        return null;
    }

    @Override
    public Result getInfoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("默认作者");
        }
        return Result.success(sysUser);
    }
}
