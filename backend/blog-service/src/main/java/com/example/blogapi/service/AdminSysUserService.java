package com.example.blogapi.service;

import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.SysUserInfoVo;
import com.example.blogapi.vo.params.BatchUpdateSysUsersParam;
import com.example.blogapi.vo.params.AdminPageParam;

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
