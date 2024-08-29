package com.example.blogapi.service;

import com.example.blogapi.dao.pojo.Message;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.MessagePageParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;
    @Test
    public void listP() {
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
    public void listN() {
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