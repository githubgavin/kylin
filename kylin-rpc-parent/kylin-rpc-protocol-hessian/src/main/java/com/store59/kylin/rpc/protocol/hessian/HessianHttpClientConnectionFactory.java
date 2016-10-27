/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.store59.kylin.rpc.protocol.hessian.ConfigurationProperties.HessianHttpClientProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * httpClient工厂类
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 15/12/16
 * @since 1.0
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/04/26
 * @since 2.0.2
 *
 */
public class HessianHttpClientConnectionFactory implements HessianConnectionFactory {
    private static final Logger log
            = LoggerFactory.getLogger(HessianHttpClientConnectionFactory.class);
    private        HessianProxyFactory _facotry;
    private static CloseableHttpClient httpClient;

    public HessianHttpClientConnectionFactory(HessianHttpClientProperties hessianHttpClientProperties) {
        init(hessianHttpClientProperties);
    }

    private void init(HessianHttpClientProperties hessianHttpClientProperties) {
        if (httpClient == null) {
            synchronized (HessianHttpClientConnectionFactory.class) {
                if (httpClient == null) {
                    HessianHttpClientProperties conf = hessianHttpClientProperties == null ?
                            new HessianHttpClientProperties() : hessianHttpClientProperties;
                    RequestConfig config = RequestConfig.custom()
                            .setConnectTimeout(conf.getConnectTimeout())
                            .setSocketTimeout(conf.getReadTimeout())
                            .setConnectionRequestTimeout(conf.getConnectTimeout())
                            .build();
                    HttpClientBuilder builder = HttpClients.custom().setDefaultRequestConfig(config);
                    builder.setMaxConnTotal(conf.getMaxConnTotal()).setMaxConnPerRoute(conf.getMaxConnPerRoute());
                    if (conf.isContentCompressionDisabled()) {
                        builder.disableContentCompression();
                    }
                    if (conf.isSystemProperties()) {
                        builder.useSystemProperties();
                    }
                    if (!StringUtils.isEmpty(conf.getUserAgent())) {
                        builder.setUserAgent(conf.getUserAgent());
                    }

                    httpClient = builder.build();
                }
            }
        }
    }

    /**
     * 单例, 对于hessian连接池httpclient, 保证一个jvm中一个host对应一个httpPost
     * 注意: 关于连接超时配置, 有全局默认配置, 也支持单独配置
     */
    private static Map<String, HttpPost> HttpPostMap = new HashMap();

    @Override
    public void setHessianProxyFactory(HessianProxyFactory factory) {
        _facotry = factory;
    }

    @Override
    public HessianConnection open(URL url)
            throws IOException {
        try {
            return new HessianHttpClientConnection(getHttpPost(url.toString()), httpClient);
        } catch (CloneNotSupportedException e) {
            throw new IOException(e);
        }
    }

    private HttpPost getHttpPost(String host) {
        if (_facotry == null) {
            new RuntimeException("_facotry should't be null");
        }
        if (!HttpPostMap.containsKey(host)) {
            synchronized (HessianHttpClientConnectionFactory.class) {
                if (!HttpPostMap.containsKey(host)) {
                    HttpPost httpPost = new HttpPost(host);
                    if (_facotry.getConnectTimeout() > 0 || _facotry.getReadTimeout() > 0) {
                        RequestConfig.Builder configBuilder = RequestConfig.custom();
                        if (_facotry.getConnectTimeout() > 0) {
                            int connectTimeout = new Long(_facotry.getConnectTimeout()).intValue();
                            configBuilder.setConnectionRequestTimeout(connectTimeout).setConnectTimeout(connectTimeout);
                        }
                        if (_facotry.getReadTimeout() > 0) {
                            configBuilder.setSocketTimeout(new Long(_facotry.getReadTimeout()).intValue());
                        }
                        httpPost.setConfig(configBuilder.build());
                    }
                    HttpPostMap.put(host, httpPost);
                }
            }
        }
        return HttpPostMap.get(host);
    }
}
