package com.example.teachapi.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类习惯前面加@Configuration
 */
@Configuration
/**
 * mapperScan 扫包，代表我们对com.example.blog.mapper文件路径下的mapper扫描
 * 扫包，将此包下的接口生成代理实现类，并且注册到spring容器中
 */
@MapperScan("com.example.blogapi.dao")
public class MybatisPlusConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
