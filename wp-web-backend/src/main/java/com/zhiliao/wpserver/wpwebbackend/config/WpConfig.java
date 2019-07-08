package com.zhiliao.wpserver.wpwebbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:28
 * @Desc:
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "server.wisdomPark")
public class WpConfig {

    private static volatile WpConfig instance = new WpConfig();

    public static WpConfig getConfiguration() {
        return instance;
    }

    private WpConfig() {
    }

    @Autowired
    private Environment environment;
}
