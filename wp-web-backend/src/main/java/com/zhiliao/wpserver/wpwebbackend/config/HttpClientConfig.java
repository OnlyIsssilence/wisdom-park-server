package com.zhiliao.wpserver.wpwebbackend.config;

import com.zhiliao.wpserver.utils.httpclient.async.AsyncHttpClientUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:28
 * @Desc:
 */
@Configuration
public class HttpClientConfig {

    @Bean(name = "wpHttpClient")
    public AsyncHttpClientUtil getWpHttpClient() {
        return new AsyncHttpClientUtil(30000, 30000, 30000, 50);
    }

}
