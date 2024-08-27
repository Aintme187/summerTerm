package com.example.blogapi.fallback;

import com.example.blogapi.dao.pojo.SysUser;
import com.example.blogapi.service.UserClient;
import com.example.blogapi.vo.ErrorCode;
import com.example.blogapi.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public Result getMyInfo(Long id){
                log.error("远程调用getMyInfo查询用户异常， 参数:" + throwable);
                SysUser sysUser = new SysUser();
                return Result.success(sysUser);
            }
        };
    }
}