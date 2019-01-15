package com.fui.cas.config;

import com.fui.cas.capcha.SessionCaptchaResultProvider;
import com.fui.cas.capcha.ValidateLoginCaptchaAction;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.execution.Action;

/**
 * @Author sf.xiong on 2018-07-17.
 */
@Configuration("captchaConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CaptchaConfiguration {

    @ConditionalOnMissingBean(name = "validateLoginCaptchaAction")
    @Bean
    @RefreshScope
    public Action validateLoginCaptchaAction() {
        return new ValidateLoginCaptchaAction(captchaResultProvider());
    }

    @Bean
    public SessionCaptchaResultProvider captchaResultProvider() {
        return new SessionCaptchaResultProvider();
    }
}
