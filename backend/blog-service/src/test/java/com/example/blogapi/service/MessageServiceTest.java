package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Message;
import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.MessagePageParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageServiceTest {
    @Autowired
    private MessageService messageService;
    @Test
    void listP() {
        MessagePageParam messagePageParam = new MessagePageParam();
        messagePageParam.setPage(1);
        messagePageParam.setPageSize(10);
        Result result = messageService.list(messagePageParam);

        Object data = result.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> messageList = objectMapper.convertValue(data, List.class);

        System.out.println(messageList.size());
        Assertions.assertNotEquals(messageList.size(), 0);
    }
    @Test
    void listN() {
        MessagePageParam messagePageParam = new MessagePageParam();
        messagePageParam.setPage(1);
        messagePageParam.setPageSize(0);
        Result result = messageService.list(messagePageParam);

        Object data = result.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> messageList = objectMapper.convertValue(data, List.class);

        System.out.println(messageList.size());
        Assertions.assertEquals(messageList.size(), 0);
    }
}