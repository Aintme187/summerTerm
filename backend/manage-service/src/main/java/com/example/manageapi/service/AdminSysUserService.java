package com.example.manageapi.service;

import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.SysUserInfoVo;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateSysUsersParam;

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
