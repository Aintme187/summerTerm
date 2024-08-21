package com.example.usercenterapi.vo.params;

import lombok.Data;

@Data
public class PageParams {
    private int page = 1;
    private int pageSize = 100;
    private String name;
    private Long categoryId;
    private Long tagId;
    private Long favoriteId;
}
