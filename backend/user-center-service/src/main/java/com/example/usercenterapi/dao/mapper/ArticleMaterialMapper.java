package com.example.usercenterapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.usercenterapi.dao.pojo.ArticleMaterial;
import com.example.usercenterapi.dao.pojo.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMaterialMapper extends BaseMapper<ArticleMaterial> {
    @Select("SELECT * FROM material WHERE course_id = #{courseId}")
    List<Material> selectMaterialsByCourseId(@Param("courseId") Long courseId);

    @Select("SELECT id, article_id, url, name, size, time FROM article_material WHERE article_id = #{articleId}")
    List<Material> findMaterialsByArticleId(Long articleId);

}