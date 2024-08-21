package com.example.teachapi.vo.params;

import com.example.teachapi.vo.SysUserInfoVo;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateSysUsersParam {
    private List<Long> ids;
    /**
     * 不改变的字段为 null
     */
    private SysUserInfoVo sysUserInfoVo;
}
