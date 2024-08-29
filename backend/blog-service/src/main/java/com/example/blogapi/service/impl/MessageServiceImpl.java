package com.example.blogapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogapi.dao.mapper.MessageMapper;
import com.example.blogapi.dao.pojo.Article;
import com.example.blogapi.dao.pojo.Message;
import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.dao.pojo.UserMessage;
import com.example.blogapi.service.MessageService;
import com.example.blogapi.service.SysUserService;
import com.example.blogapi.service.UserClient;
import com.example.blogapi.utils.UserThreadLocal;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.MessagePageParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserClient userClient;

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

        if(sysUser == null){
            Result result = userClient.getMyInfo(1L);
            Object data = result.getData();
            ObjectMapper objectMapper = new ObjectMapper();
            sysUser = objectMapper.convertValue(data, SysUser.class);

        }
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
