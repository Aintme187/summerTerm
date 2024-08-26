package com.example.manageapi.vo.params;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.manageapi.dao.dto.SysUser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
public class TryAdminPageParam {
    private AdminPageParam adminPageParam;
    private QueryWrapper<SysUser> queryWrapper;
}
