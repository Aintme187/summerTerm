package com.example.manageapi.vo.params;

import com.example.manageapi.dao.dto.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCurriculumsParam {
    private List<Long> ids;
    private Curriculum curriculum;
}
