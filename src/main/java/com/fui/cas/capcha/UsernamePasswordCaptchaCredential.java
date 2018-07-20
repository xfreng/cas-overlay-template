package com.fui.cas.capcha;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.authentication.UsernamePasswordCredential;

import javax.validation.constraints.Size;

/**
 * @Author sf.xiong on 2018-07-16.
 */
public class UsernamePasswordCaptchaCredential extends UsernamePasswordCredential {

    @Size(min = 4, max = 4, message = "rand.required")
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public UsernamePasswordCaptchaCredential setCaptcha(String captcha) {
        this.captcha = captcha;
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.captcha)
                .toHashCode();
    }
}
