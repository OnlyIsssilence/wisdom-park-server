package com.zhiliao.wpserver.utils.httpclient.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpResponse;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:06
 * @Desc:
 */
@Slf4j
public class AsyncHandlerAdapter implements IHandler {

    @Override
    public Object failed(Exception e) {
        log.error(Thread.currentThread().getName() + " failure::" + e.getClass().getName() + "::" + ExceptionUtils.getFullStackTrace(e));
        return null;
    }
    @Override
    public Object completed(HttpResponse httpResponse) {
        log.debug(Thread.currentThread().getName() + " success::" + httpResponse.toString());
        return null;
    }
    @Override
    public Object cancelled() {
        log.warn(Thread.currentThread().getName() + " cancel");
        return null;
    }

}
