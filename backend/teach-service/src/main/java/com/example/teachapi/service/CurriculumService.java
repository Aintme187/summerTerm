package com.example.teachapi.service;

import com.example.teachapi.dao.pojo.Curriculum;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.example.teachapi.vo.params.BatchUpdateCurriculumsParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface CurriculumService {
    Curriculum selectById(Long id);

    Result selectPage(AdminPageParam adminPageParam);

    void insert(Curriculum curriculum);

    void updateById(Curriculum curriculum);

    Result deleteById(Long id);

    List<Curriculum> selectBatchIds(List<Long> ids);

    Result deleteBatchIds(List<Long> ids);

    Result update(BatchUpdateCurriculumsParam batchUpdateCurriculumsParam);
}
