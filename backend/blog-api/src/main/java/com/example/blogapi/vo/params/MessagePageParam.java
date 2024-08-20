package com.example.blogapi.vo.params;

import lombok.Data;

@Data
public class MessagePageParam {
     private int page = 1;
     private int pageSize = 100;
}
