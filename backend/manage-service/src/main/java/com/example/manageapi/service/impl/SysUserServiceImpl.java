package com.example.manageapi.service.impl;

import com.example.manageapi.client.SysuserClient;
import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysuserClient sysuserClient;
    @Override
    public SysUser findUserByAccount(String account) {
        return sysuserClient.findByAccount(account);
    }
}
