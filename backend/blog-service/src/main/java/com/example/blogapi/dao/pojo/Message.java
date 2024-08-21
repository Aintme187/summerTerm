package com.example.blogapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;

@Data
public class Message {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String sendNickname;
    private String sendAvatar;
    private String sendContent;
    private String articleTitle;
    private Long articleId;
    private Long toUid;
    private String createDate;
}
