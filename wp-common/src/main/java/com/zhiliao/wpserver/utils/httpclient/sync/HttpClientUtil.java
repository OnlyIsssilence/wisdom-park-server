package com.zhiliao.wpserver.utils.httpclient.sync;

import com.alibaba.fastjson.JSONObject;
import com.zhiliao.wpserver.utils.httpclient.BaseHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:06
 * @Desc:
 */
@Slf4j
public class HttpClientUtil extends BaseHttpClientUtil {

    private CloseableHttpClient httpClient;

    public HttpClientUtil(int connectionRequestTimeout, int connectTimeout, int socketTimeout, int maxConnections) {
        super(connectionRequestTimeout, connectTimeout, socketTimeout, maxConnections);
        initPoolingHttpClientConnectionManager();
        httpClient = getHttpClient();
    }

    public JSONObject get(
            String scheme, String url, Map<String, String> urlParams, Map<String, String> headerParams
    ) throws IOException, URISyntaxException {
        HttpGet httpGet = new HttpGet(getURI(scheme, url, urlParams));
        try {
            return getJSONResponse(getResponse(httpGet, headerParams));
        } finally {
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
        }
    }

    public JSONObject post(
            String scheme, String url, Map<String, String> urlParams, Map<String, String> headerParams)
            throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost(getURI(scheme, url, urlParams));
        try {
            return getJSONResponse(getResponse(httpPost, headerParams));
        } finally {
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
    }

    private HttpResponse getResponse(HttpRequestBase htb, Map<String, String> headerParams)
            throws IOException {
        htb.setHeaders(getHeaders(headerParams));
        HttpResponse response = httpClient.execute(htb);
        return response;
    }

    public JSONObject postJSON(
            String scheme, String url, Map<String, String> urlParams,
            Map<String, String> headerParams, JSONObject requestBody
    ) throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost(getURI(scheme, url, urlParams));
        try {
            StringEntity entity = new StringEntity(requestBody.toJSONString());

            httpPost.setHeaders(getHeaders(headerParams));
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            return getJSONResponse(response);
        } finally {
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
    }

}
