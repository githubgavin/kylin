/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.metrics.atlas.AtlasTagProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/15
 * @since 1.0
 */
@Configuration
public class AtlasTagProviderConfigration {

    @Bean
    AtlasTagProvider atlasCommonTags(@Value("${spring.application.name}") String appName) {
        return () -> Collections.singletonMap("app", appName);
    }

}
