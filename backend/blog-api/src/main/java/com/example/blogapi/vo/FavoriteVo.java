package com.example.blogapi.vo;

import lombok.Data;

@Data
public class FavoriteVo {
    private Long id;
    private String favoriteName;
    private String avatar;
    private Long count;
}
