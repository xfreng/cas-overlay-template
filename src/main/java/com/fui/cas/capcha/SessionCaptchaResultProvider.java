package com.fui.cas.capcha;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * session提供
 *
 * @Author sf.xiong on 2018-07-17.
 */
public class SessionCaptchaResultProvider implements ICaptchaResultProvider<HttpSession, String> {

    @Override
    public void store(HttpSession httpSession, String s) {
        httpSession.setAttribute(CaptchaConstants.STORE_CODE, s);
    }

    @Override
    public String get(HttpSession httpSession) {
        return (String) httpSession.getAttribute(CaptchaConstants.STORE_CODE);
    }

    @Override
    public boolean validate(HttpSession store, String code) {
        String relCode = get(store);
        if (!StringUtils.isEmpty(relCode) && relCode.equals(code)) {
            //校验完清空
            store(store, null);
            return true;
        }
        return false;
    }
}
