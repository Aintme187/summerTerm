package com.example.manageapi.client;

import com.example.manageapi.dao.dto.Course;
import com.example.manageapi.dao.dto.Curriculum;
import com.example.manageapi.vo.Result;
import com.example.manageapi.vo.params.AdminPageParam;
import com.example.manageapi.vo.params.BatchUpdateCoursesParam;
import com.example.manageapi.vo.params.BatchUpdateCurriculumsParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "teach-service")
public interface teachClient {
    @GetMapping("/course/selectById")
    Course selectById(@RequestParam("id") Long id);

    @PostMapping("/course/selectJoinPage")
    Result selectJoinPage(@RequestBody AdminPageParam adminPageParam);

    @PostMapping("/course/insert")
    void insert(@RequestBody Course course);

    @PutMapping("/course/updateById")
    void updateById(@RequestBody Course course);

    @DeleteMapping("/course/deleteById")
    Result deleteById(@RequestParam Long id);

    @GetMapping("/course/selectBatchIds")
    List<Course> selectBatchIds(@RequestParam List<Long> ids);

    @DeleteMapping("/course/DeleteBatchByIds")
    Result deleteBatchIds(@RequestParam List<Long> ids);

    @PutMapping("/course/update")
    Result update(@RequestBody BatchUpdateCoursesParam batchUpdateCoursesParam);

    @GetMapping("/curriculum/selectById")
    Curriculum selectCurriculumById(@RequestParam("id") Long id);

    @PostMapping("/curriculum/selectPage")
    Result selectPage(@RequestBody AdminPageParam adminPageParam);

    @PostMapping("/curriculum/insert")
    void insert(@RequestBody Curriculum curriculum);

    @PutMapping("/curriculum/updateById")
    void updateById(@RequestBody Curriculum curriculum);

    @DeleteMapping("/curriculum/deleteById")
    Result deleteCurriculumById(@RequestParam Long id);

    @GetMapping("/curriculum/selectBatchIds")
    List<Curriculum> selectCurriculumBatchIds(@RequestParam List<Long> ids);

    @DeleteMapping("/curriculum/DeleteBatchByIds")
    Result deleteCurriculumBatchIds(@RequestParam List<Long> ids);

    @PutMapping("/curriculum/update")
    Result update(@RequestBody BatchUpdateCurriculumsParam batchUpdateCurriculumsParam);
}
