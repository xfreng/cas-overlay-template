package com.fui.cas.capcha;

import org.apereo.cas.web.flow.AbstractCasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author sf.xiong on 2018-07-16.
 */
public class ValidateWebflowConfigurer extends AbstractCasWebflowConfigurer {

    public ValidateWebflowConfigurer(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry flowDefinitionRegistry) {
        super(flowBuilderServices, flowDefinitionRegistry);
    }

    @Override
    protected void doInitialize() throws Exception {
        final Flow flow = getLoginFlow();
        bindCredential(flow);
        createLoginValidateValidateFlow(flow);
    }

    /**
     * 绑定输入信息
     *
     * @param flow
     */
    protected void bindCredential(Flow flow) {
        if (flow != null) {
            //重写绑定自定义credential
            createFlowVariable(flow, CasWebflowConstants.VAR_ID_CREDENTIAL, UsernamePasswordCaptchaCredential.class);
            //登录页绑定新参数
            final ViewState state = (ViewState) flow.getState(CasWebflowConstants.STATE_ID_VIEW_LOGIN_FORM);
            final BinderConfiguration cfg = getViewStateBinderConfiguration(state);
            //由于用户名以及密码已经绑定，所以只需对新加系统参数绑定即可
            cfg.addBinding(new BinderConfiguration.Binding(CaptchaConstants.CODE_PARAM, null, false));
        }
    }

    /**
     * 登录校验流程
     */
    private void createLoginValidateValidateFlow(Flow flow) {
        if (flow != null) {
            final ActionState state = (ActionState) flow.getState(CasWebflowConstants.TRANSITION_ID_REAL_SUBMIT);
            final List<Action> currentActions = new ArrayList<Action>();
            state.getActionList().forEach(currentActions::add);
            currentActions.forEach(a -> state.getActionList().remove(a));

            state.getActionList().add(createEvaluateAction("validateLoginCaptchaAction"));
            currentActions.forEach(a -> state.getActionList().add(a));

            state.getTransitionSet().add(createTransition(CaptchaConstants.CAPTCHA_CODE, CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM));
        }
    }
}
