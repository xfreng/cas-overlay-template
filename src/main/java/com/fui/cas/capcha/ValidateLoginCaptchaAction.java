package com.fui.cas.capcha;

import org.apereo.cas.authentication.Credential;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author sf.xiong on 2018-07-17.
 */
public class ValidateLoginCaptchaAction extends AbstractAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateLoginCaptchaAction.class);

    //验证码存储器
    private ICaptchaResultProvider<HttpSession, String> captchaResultProvider;

    public ValidateLoginCaptchaAction(ICaptchaResultProvider<HttpSession, String> captchaResultProvider) {
        this.captchaResultProvider = captchaResultProvider;
    }

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        Credential credential = WebUtils.getCredential(context);
        //系统信息不为空才检测校验码
        if (credential instanceof UsernamePasswordCaptchaCredential && ((UsernamePasswordCaptchaCredential) credential).getCaptcha() != null) {
            if (isEnable()) {
                LOGGER.debug("开始校验登录校验码");
                HttpServletRequest request = WebUtils.getHttpServletRequest();
                HttpSession httpSession = request.getSession();
                //校验码
                String inCode = request.getParameter(CaptchaConstants.CODE_PARAM);
                //校验码失败跳转到登录页
                if (!this.captchaResultProvider.validate(httpSession, inCode)) {
                    return getError(context, CaptchaConstants.CAPTCHA_CODE);
                }
            }
        }
        return null;
    }

    /**
     * 是否开启验证码
     *
     * @return
     */
    private boolean isEnable() {
        return true;
    }

    /**
     * 跳转到错误页
     *
     * @param requestContext
     * @return Event
     */
    private Event getError(final RequestContext requestContext, final String code) {
        final MessageContext messageContext = requestContext.getMessageContext();
        messageContext.addMessage(new MessageBuilder().error().code(code).build());
        return getEventFactorySupport().event(this, code);
    }
}
