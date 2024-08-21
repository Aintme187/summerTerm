package com.example.usercenterapi.service;

import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.SysUserInfoVo;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.BatchUpdateSysUsersParam;

import java.util.List;

public interface AdminSysUserService {
    Result listSysUserPage(AdminPageParam adminPageParam);

    Result getSysUserInfoById(Long id);

    Result addSysUser(SysUserInfoVo sysUserInfoVo);

    Result updateSysUser(SysUserInfoVo sysUserInfoVo);

    Result deleteSysUser(Long id);

    Result batchDeleteSysUsers(List<Long> ids);

    Result batchUpdateSysUsers(BatchUpdateSysUsersParam batchUpdateSysUsersParam);
}
