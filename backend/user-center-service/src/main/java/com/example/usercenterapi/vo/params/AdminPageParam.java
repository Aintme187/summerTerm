package com.example.usercenterapi.vo.params;

import lombok.Data;

import java.util.List;

@Data
public class AdminPageParam {
    private Long page;
    private Long pageSize;
    private List<FilterData> filterDataList;
}
