package com.example.blogapi;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;

import java.util.Properties;

public class NacosConfigSender {

    public static void main(String[] args) throws Exception {
        final String groupId = "DEFAULT_GROUP";
        final String dataId = "sentinel-demo-flow-rules";
        // 创建ConfigService实例
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, "10.192.123.91:8848");
        // 指定namespace
        properties.put(PropertyKeyConst.NAMESPACE, "PUBLIC");
        final String rule = "[\n"
            + "  {\n"
            + "    \"resource\": \"GET:/user/getById\",\n"
            + "    \"controlBehavior\": 0,\n"
            + "    \"count\": 1,\n"
            + "    \"grade\": 1,\n"
            + "    \"limitApp\": \"default\",\n"
            + "    \"strategy\": 0\n"
            + "  }\n"
            + "]";
        ConfigService configService = NacosFactory.createConfigService(properties);
        System.out.println(configService.publishConfig(dataId, groupId, rule));
    }
}