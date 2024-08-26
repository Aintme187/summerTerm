package com.example.usercenterapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenterapi.dao.dto.TryAdminPageParam;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.UserVo;
import com.example.usercenterapi.vo.params.AdminPageParam;
import com.example.usercenterapi.vo.params.BatchUpdateSysUsersParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    SysUser getUserInfo(String account, String password);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long id);

    Result updateUser(SysUser sysUser);

    Result getInfoById(Long id);

    Result updateAvatar(Long id,String url);

    SysUser selectById(Long id);

    Result selectPage(AdminPageParam adminPageParam);

    void insert(SysUser sysUser);

    void updateById(SysUser sysUser);

    Result deleteById(Long id);

    List<SysUser> selectBatchIds(List<Long> ids);

    Result deleteBatchIds(List<Long> ids);

    Result update(BatchUpdateSysUsersParam batchUpdateSysUsersParam);
}
