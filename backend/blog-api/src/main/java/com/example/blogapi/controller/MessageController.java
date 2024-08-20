package com.example.blogapi.controller;

import com.example.blogapi.service.MessageService;
import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.MessagePageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
