package com.example.usercenterapi.controller;

import com.example.usercenterapi.service.MessageService;
import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.MessagePageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping("list")
    public Result list(@RequestBody MessagePageParam messagePageParam){
        return messageService.list(messagePageParam);

    }
}
