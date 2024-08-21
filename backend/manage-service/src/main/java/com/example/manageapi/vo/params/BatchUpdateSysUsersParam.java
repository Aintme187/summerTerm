package com.example.manageapi.vo.params;

import com.example.manageapi.vo.SysUserInfoVo;
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
