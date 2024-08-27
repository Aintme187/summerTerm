package com.example.teachapi.controller;

import com.example.teachapi.dao.pojo.Curriculum;
import com.example.teachapi.service.CurriculumService;
import com.example.teachapi.vo.Result;
import com.example.teachapi.vo.params.AdminPageParam;
import com.example.teachapi.vo.params.BatchUpdateCurriculumsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ddddm
 **/
@RestController
@RequestMapping("curriculum")
@CrossOrigin
public class CurriculumController {
    @Autowired
    private CurriculumService curriculumService;

    @GetMapping("/selectById")
    public Curriculum selectById(@RequestParam("id") Long id){
        return curriculumService.selectById(id);
    }

    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody AdminPageParam adminPageParam) {
        return curriculumService.selectPage(adminPageParam);
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Curriculum curriculum) {
        curriculumService.insert(curriculum);
    }

    @PutMapping("/updateById")
    public void updateById(@RequestBody Curriculum curriculum) {
        curriculumService.updateById(curriculum);
    }

    @DeleteMapping("/deleteById")
    Result deleteById(@RequestParam Long id) {
        return curriculumService.deleteById(id);
    }

    @GetMapping("/selectBatchIds")
    List<Curriculum> selectBatchIds(@RequestParam List<Long> ids) {
        return curriculumService.selectBatchIds(ids);
    }

    @DeleteMapping("/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids) {
        return curriculumService.deleteBatchIds(ids);
    }

    @PutMapping("/update")
    Result update(@RequestBody BatchUpdateCurriculumsParam batchUpdateCurriculumsParam) {
        return curriculumService.update(batchUpdateCurriculumsParam);
    }
}
