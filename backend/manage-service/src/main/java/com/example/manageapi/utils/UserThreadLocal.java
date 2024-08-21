package com.example.manageapi.utils;

import com.example.manageapi.dao.pojo.SysUser;


public class UserThreadLocal {

    private UserThreadLocal(){}
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
