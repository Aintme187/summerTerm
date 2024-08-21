package com.example.usercenterapi.service;

import com.example.usercenterapi.vo.Result;
import com.example.usercenterapi.vo.params.MessagePageParam;

public interface MessageService {
    Result list(MessagePageParam messagePageParam);
}
