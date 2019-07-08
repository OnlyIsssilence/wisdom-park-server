package com.zhiliao.wpserver.utils.httpclient.async;

import org.apache.http.HttpResponse;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:06
 * @Desc:
 */
public interface IHandler {

    /**
     * 处理异常时，执行该方法
     *
     * @return
     */
    Object failed(Exception e);

    /**
     * 处理正常时，执行该方法
     *
     * @return
     */
    Object completed(HttpResponse httpResponse);

    /**
     * 处理取消时，执行该方法
     *
     * @return
     */
    Object cancelled();

}
