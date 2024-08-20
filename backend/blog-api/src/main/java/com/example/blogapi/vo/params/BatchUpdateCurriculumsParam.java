package com.example.blogapi.vo.params;

import com.example.blogapi.dao.pojo.Curriculum;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateCurriculumsParam {
    private List<Long> ids;
    private Curriculum curriculum;
}
