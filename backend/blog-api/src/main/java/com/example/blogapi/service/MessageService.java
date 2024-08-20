package com.example.blogapi.service;

import com.example.blogapi.vo.Result;
import com.example.blogapi.vo.params.MessagePageParam;

public interface MessageService {
    Result list(MessagePageParam messagePageParam);
}
