package com.example.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.usercenterapi.dao.mapper.MessageMapper;
import com.example.usercenterapi.dao.pojo.Message;
import com.example.usercenterapi.dao.pojo.SysUser;
import com.example.usercenterapi.service.MessageService;
import com.example.usercenterapi.utils.UserThreadLocal;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.MessagePageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 1.根据当前登录用户获取用户id，构建分页page
     * 2.根据用户信息关联表查出所有消息id，通过join返回消息列表
     * 3.
     * @param messagePageParam
     * @return
     */
    @Override
    public Result list(MessagePageParam messagePageParam) {
        SysUser sysUser = UserThreadLocal.get();
        Long sysUserId = sysUser.getId();
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getToUid, sysUserId);
        queryWrapper.orderByDesc(Message::getCreateDate);
        Page<Message> page = new Page<>(messagePageParam.getPage(), messagePageParam.getPageSize());

        Page<Message> messagePage = messageMapper.selectPage(page, queryWrapper);
        List<Message> messageList = messagePage.getRecords();
        return Result.success(messageList);
    }
}
