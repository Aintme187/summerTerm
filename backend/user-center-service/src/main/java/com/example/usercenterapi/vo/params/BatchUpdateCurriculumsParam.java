package com.example.usercenterapi.vo.params;

import com.example.usercenterapi.dao.pojo.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCurriculumsParam {
    private List<Long> ids;
    private Curriculum curriculum;
}
