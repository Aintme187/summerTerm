package com.example.manageapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manageapi.client.SysuserClient;
import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.service.AdminSysUserService;
import com.example.manageapi.service.SysUserService;
import com.example.manageapi.vo.AdminSysUserVo;
import com.example.manageapi.vo.ErrorCode;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.SysUserInfoVo;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateSysUsersParam;
import com.example.manageapi.vo.params.FilterData;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AdminSysUserServiceImpl implements AdminSysUserService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysuserClient sysuserClient;

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
    public Result listSysUserPage(AdminPageParam adminPageParam) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        List<FilterData> filterDataList = adminPageParam.getFilterDataList();
        if (FilterData.injectFilter(queryWrapper, filterDataList)) {
            AdminSysUserVo adminSysUserVo = new AdminSysUserVo();
            try {
                Page<SysUser> sysUserPage = sysuserClient.selectPage(new Page<>(adminPageParam.getPage(), adminPageParam.getPageSize()), queryWrapper);
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

    @Override
    public Result getSysUserInfoById(Long id) {
        SysUser sysUser = sysuserClient.selectById(id);
        if (sysUser == null) {
            return Result.fail(ErrorCode.NO_USER);
        }
        SysUserInfoVo sysUserInfoVo = new SysUserInfoVo();
        BeanUtils.copyProperties(sysUser, sysUserInfoVo);
        return Result.success(sysUserInfoVo);
    }

    @Override
    public Result addSysUser(SysUserInfoVo sysUserInfoVo) {
        if (sysUserService.findUserByAccount(sysUserInfoVo.getAccount()) != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST);
        }
        // bug here
        // 账号密码加盐约束条件待补充
        // salt here is ""
        // avatar here is meaningless
//        sysUserInfoVo.setPassword(DigestUtils.md5Hex(sysUserInfoVo.getPassword() + salt));
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserInfoVo, sysUser);
        sysUser.setCreateDate(new Date(System.currentTimeMillis()));
        sysUser.setSalt(salt);
        sysUser = noNull(sysUser);
        ErrorCode errorCode = errorInSysUser(sysUser);
        if (errorCode != null) return Result.fail(errorCode);
        try {
            sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword() + salt));
            sysuserClient.insert(sysUser);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public Result updateSysUser(SysUserInfoVo sysUserInfoVo) {
        SysUser sysUser = sysUserService.findUserByAccount(sysUserInfoVo.getAccount());
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST);
        }
        // bug here
        // 账号密码加盐约束条件待补充
        // salt here is ""
        // avatar here is meaningless
//        sysUserInfoVo.setPassword(DigestUtils.md5Hex(sysUserInfoVo.getPassword() + salt));
        BeanUtils.copyProperties(sysUserInfoVo, sysUser);
        ErrorCode errorCode = errorInSysUser(sysUser);
        if (errorCode != null) return Result.fail(errorCode);
        try {
            if (sysUser.getPassword() != null) sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword() + salt));
            sysUser.setPermission(null);
            sysuserClient.updateById(sysUser);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
        }
    }

    @Override
    public Result deleteSysUser(Long id) {
        if (sysuserClient.selectById(id) == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            return sysuserClient.deleteById(id);
        }
    }

    @Override
    public Result batchDeleteSysUsers(List<Long> ids) {
        if (sysuserClient.selectBatchIds(ids).size() != ids.size()) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        } else {
            return sysuserClient.deleteBatchIds(ids);
        }
    }

    @Override
    public Result batchUpdateSysUsers(BatchUpdateSysUsersParam batchUpdateSysUsersParam) {
        List<Long> ids = batchUpdateSysUsersParam.getIds();
        SysUserInfoVo sysUserInfoVo = batchUpdateSysUsersParam.getSysUserInfoVo();
        /*
        因为可能更新为空字符串 ""
        所以指定 null 为不更新
         */
        if (sysUserInfoVo.getPassword() != null) {
//            sysUserInfoVo.setPassword(DigestUtils.md5Hex(sysUserInfoVo.getPassword()));
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
            sysuserClient.update(sysUser, updateWrapper);
            return Result.success(null);
        } catch (Exception e) {
            return Result.fail(ErrorCode.DATA_ERROR);
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

    /**
     * 转化为一个没有 null 字段（除了 id ）的 sysUser
     *
     * @param sysUser 待转换的
     * @return 转换得到的
     */
    private SysUser noNull(SysUser sysUser) {
        SysUser noNullSysUser = new SysUser(null, "", "", new Date(), "", "", "", "", "", "", "", "");
        BeanUtils.copyProperties(sysUser, noNullSysUser);
        return noNullSysUser;
    }
}
