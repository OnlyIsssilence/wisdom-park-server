package com.zhiliao.wpserver.utils.httpclient;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
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
public class BaseHttpClientUtil {

    /**
     * 连接池获取到连接的超时时间
     */
    private int connectionRequestTimeout;

    /**
     * 建立连接的超时
     */
    private int connectTimeout;

    /**
     * 获取数据的超时时间
     */
    private int socketTimeout;

    /**
     * 最大连接数
     */
    private int maxConnections;

    protected static final Charset CHAR_SET = Charset.forName("utf-8");

    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    private PoolingNHttpClientConnectionManager poolingNIOHttpClientConnectionManager;

    protected void initPoolingHttpClientConnectionManager() {
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(maxConnections);
        poolingHttpClientConnectionManager.setDefaultConnectionConfig(ConnectionConfig.custom()
                .setCharset(CHAR_SET)
                .build());
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(socketTimeout)
                .setSoReuseAddress(true)
                .build();
        poolingHttpClientConnectionManager.setDefaultSocketConfig(socketConfig);
    }

    protected void initPoolNIOHttpClientConnectionManager() {
        try {
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
            poolingNIOHttpClientConnectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
            poolingNIOHttpClientConnectionManager.setMaxTotal(maxConnections);
            poolingNIOHttpClientConnectionManager.setDefaultConnectionConfig(
                    ConnectionConfig.custom()
                            .setCharset(CHAR_SET)
                            .build()
            );
        } catch (IOReactorException e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    public BaseHttpClientUtil(int connectionRequestTimeout, int connectTimeout, int socketTimeout, int maxConnections) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
        this.maxConnections = maxConnections;
    }

    protected CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(getRequestConfig())
                .build();
        if (poolingHttpClientConnectionManager != null
                && poolingHttpClientConnectionManager.getTotalStats() != null) {
            log.info("now client pool {}", poolingHttpClientConnectionManager.getTotalStats().toString());
        }
        return httpClient;
    }

    protected CloseableHttpAsyncClient getHttpAsyncClient() {
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(poolingNIOHttpClientConnectionManager)
                .setDefaultRequestConfig(getRequestConfig())
                .build();
        if (poolingNIOHttpClientConnectionManager != null
                && poolingNIOHttpClientConnectionManager.getTotalStats() != null) {
            //打印连接池的状态
            log.info("now client pool {}", poolingNIOHttpClientConnectionManager.getTotalStats().toString());
        }
        return httpAsyncClient;
    }

    protected RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
    }

    protected URI getURI(String scheme, String url, Map<String, String> urlParams) throws URISyntaxException {
        //通过URIBuilder类创建URI
        URI uri;
        if (null != urlParams) {
            uri = new URIBuilder().setScheme(scheme)
                    .setHost(url)
                    .setParameters(getNameValuePair(urlParams))
                    .build();
        } else {
            uri = new URIBuilder().setScheme(scheme)
                    .setHost(url)
                    .build();
        }
        return uri;
    }

    private List<NameValuePair> getNameValuePair(Map<String, String> params) {
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        params.forEach((k, v) -> nameValuePairList.add(new BasicHeader(k, v)));
        return nameValuePairList;
    }

    protected Header[] getHeaders(Map<String, String> headerParamas) {
        if (null == headerParamas) {
            return null;
        }
        List<Header> headerList = new ArrayList<>();
        headerParamas.forEach((name, value) -> headerList.add(new BasicHeader(name, value)));
        return headerList.toArray(new Header[0]);
    }

    public static JSONObject getJSONResponse(HttpResponse httpResponse) {
        JSONObject response = new JSONObject();
        try {
            InputStream input = httpResponse.getEntity().getContent();
            response.put("code", httpResponse.getStatusLine().getStatusCode());
            response.put("body", IOUtils.toString(input, CHAR_SET));
        } catch (IOException e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;

    }

    public static String extractCookieValue(ServletRequest request, String cookieKey) {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie cookie = findCookie(req, cookieKey);
        return cookie == null ? null : cookie.getValue().trim();
    }

    public static Cookie findCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }

        return null;
    }

}
