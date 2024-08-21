package com.example.manageapi.service;

import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.MessagePageParam;

public interface MessageService {
    Result list(MessagePageParam messagePageParam);
}
