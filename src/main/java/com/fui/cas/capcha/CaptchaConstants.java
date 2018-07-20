package com.fui.cas.capcha;

/**
 * 验证码常量
 *
 * @Author sf.xiong on 2018-07-17.
 */
public interface CaptchaConstants {

    /**
     * 验证码存储常量，可以存储session等等
     */
    String STORE_CODE = "KAPTCHA_SESSION_KEY";

    /**
     * 前端验证码
     */
    String CODE_PARAM = "captcha";

    /**
     * 验证码错误码
     */
    String CAPTCHA_CODE = "required.rand.error";
}
