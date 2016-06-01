/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * httpClient工厂类
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 15/12/16
 * @since 1.0
 *
 * 更新内容, httpclient参数优化, httpclient相同host的连接池复用
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.0.2 16/04/26
 * @since 2.0.2
 *
 */
public class HessianHttpClientFactory implements HessianConnectionFactory {
    private static final Logger log
            = Logger.getLogger(HessianHttpClientFactory.class.getName());
    private HessianProxyFactory _facotry;

    /**
     *
     *  单例, 对于hessian连接池httpclient, 保证一个jvm中一个host对应一个httpclient
     *  注意: 只有第一个初始化的httpclient参数生效, 其他参数不生效
     *
     * */
    private static Map<String, CloseableHttpClient> httpClientMap = new HashMap();

    @Override
    public void setHessianProxyFactory(HessianProxyFactory factory) {
        _facotry = factory;
    }

    @Override
    public HessianConnection open(URL url)
            throws IOException {
        if (log.isLoggable(Level.FINER))
            log.finer(this + " open(" + url + ")");
        return new HessianHttpClient(url, getHttpClient(url.getHost()));
    }

    private CloseableHttpClient getHttpClient(String host) {
        if (_facotry == null) {
            new RuntimeException("_facotry should't be null");
        }
        if (!httpClientMap.containsKey(host)) {
            synchronized (HessianHttpClientFactory.class) {
                if (!httpClientMap.containsKey(host)) {
                    int connectTimeout = _facotry.getConnectTimeout() > 0 ? new Long(_facotry.getConnectTimeout()).intValue() : 3000;
                    int readTimeout = _facotry.getReadTimeout() > 0 ? new Long(_facotry.getReadTimeout()).intValue() : 3000;
                    RequestConfig config = RequestConfig.custom()
                            .setConnectTimeout(connectTimeout)
                            .setSocketTimeout(readTimeout)
                            .setStaleConnectionCheckEnabled(true)
                            .setConnectionRequestTimeout(connectTimeout)
                            .build();
                    HttpClientBuilder builder = HttpClients.custom().setDefaultRequestConfig(config);
                    if (_facotry instanceof KylinHessianProxyFactory) {
                        KylinHessianProxyFactory kpf = (KylinHessianProxyFactory) _facotry;
                        builder.setMaxConnTotal(kpf.getMaxConnTotal()).setMaxConnPerRoute(kpf.getMaxConnPerRoute());
                        if (kpf.isContentCompressionDisabled()) {
                            builder.disableContentCompression();
                        }
                        if (kpf.isSystemProperties()) {
                            builder.useSystemProperties();
                        }
                        if (!StringUtils.isEmpty(kpf.getUserAgent())) {
                            builder.setUserAgent(kpf.getUserAgent());
                        }
                    }
                    CloseableHttpClient httpClient = builder.build();
                    httpClientMap.put(host, httpClient);
                }
            }
        }
        return httpClientMap.get(host);
    }
}
