package com.zhiliao.wpserver.utils.httpclient.async;

import com.alibaba.fastjson.JSONObject;
import com.zhiliao.wpserver.utils.httpclient.BaseHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:06
 * @Desc:
 */
@Slf4j
public class AsyncHttpClientUtil extends BaseHttpClientUtil {

    private CloseableHttpAsyncClient closeableHttpAsyncClient;

    public AsyncHttpClientUtil(int connectionRequestTimeout, int connectTimeout, int socketTimeout, int maxConnections) {
        super(connectionRequestTimeout, connectTimeout, socketTimeout, maxConnections);
        initPoolNIOHttpClientConnectionManager();
        closeableHttpAsyncClient = getHttpAsyncClient();
    }

    public Future<HttpResponse> get(
            String scheme, String url, Map<String, String> urlParams, Map<String, String> headerParams,
            final AsyncHandlerAdapter handler) throws IOException, URISyntaxException {
        HttpGet httpGet = new HttpGet(getURI(scheme, url, urlParams));
        httpGet.setHeaders(getHeaders(headerParams));
        return execute(closeableHttpAsyncClient, httpGet, handler);
    }

    public Future<HttpResponse> postJSON(
            String scheme, String url, Map<String, String> urlParams, Map<String, String> headerParams,
            JSONObject requestBody, final IHandler handler) throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost(getURI(scheme, url, urlParams));
        StringEntity entity = new StringEntity(requestBody.toJSONString());
        httpPost.setConfig(getRequestConfig());
        httpPost.setEntity(entity);
        httpPost.setHeaders(getHeaders(headerParams));
        return execute(closeableHttpAsyncClient, httpPost, handler);
    }

    private Future<HttpResponse> execute(
            CloseableHttpAsyncClient closeableHttpAsyncClient, HttpRequestBase httpRequest, final IHandler handler) {
        if (!closeableHttpAsyncClient.isRunning()) {
            closeableHttpAsyncClient.start();
        }
        return closeableHttpAsyncClient.execute(httpRequest, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse response) {
                handler.completed(response);
                releaseConnection(httpRequest);
            }

            @Override
            public void failed(final Exception ex) {
                handler.failed(ex);
                releaseConnection(httpRequest);
            }

            @Override
            public void cancelled() {
                handler.cancelled();
                releaseConnection(httpRequest);
            }
        });
    }

    private static void close(CloseableHttpAsyncClient client) {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseConnection(HttpRequestBase httpRequest) {
        httpRequest.releaseConnection();
    }

}
