package com.fui.cas.capcha;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.pm.config.PasswordManagementConfiguration;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * @Author sf.xiong on 2018-07-16.
 */
@Configuration("fuiWebflowConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@AutoConfigureAfter(value = PasswordManagementConfiguration.class)
public class ValidateWebflowConfiguration {

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowRegistry;

    @Autowired
    @Qualifier("builder")
    private FlowBuilderServices builder;

    @Bean("fuiWebflowConfigurer")
    public CasWebflowConfigurer fuiWebflowConfigurer() {
        return new ValidateWebflowConfigurer(builder, loginFlowRegistry);
    }
}