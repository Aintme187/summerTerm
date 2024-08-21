package com.example.teachapi.vo.params;

import com.example.teachapi.dao.pojo.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCurriculumsParam {
    private List<Long> ids;
    private Curriculum curriculum;
}
