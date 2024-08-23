package com.example.teachapi.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachapi.dao.pojo.ArticleMaterial;
import com.example.teachapi.dao.pojo.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMaterialMapper extends BaseMapper<ArticleMaterial> {

    @Select("SELECT id, article_id, url, name, size, time FROM article_material WHERE article_id = #{articleId}")
    List<Material> findMaterialsByArticleId(Long articleId);

}