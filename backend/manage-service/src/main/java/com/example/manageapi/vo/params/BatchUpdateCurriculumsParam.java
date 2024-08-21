package com.example.manageapi.vo.params;

import com.example.manageapi.dao.pojo.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCurriculumsParam {
    private List<Long> ids;
    private Curriculum curriculum;
}
