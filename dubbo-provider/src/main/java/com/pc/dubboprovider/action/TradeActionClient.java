package com.pc.dubboprovider.action;

import com.pc.dubboprovider.action.param.PayParam;
import com.pc.tpt.core.bean.BeanHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author pengchao
 * @date 14:18 2020-09-21
 */
@Component
public class TradeActionClient {
    @Resource
    private BeanHelper beanHelper;

    /**
     * 执行器
     *
     * @param params
     * @return
     */
    public <P extends PayParam, R extends PayResult> R doBusiness(P params) {
        String beanName = ActionBeanName.getName(params.getClass());
        try {
            TradeAction tradeAction = beanHelper.getBean(beanName, TradeAction.class);
            R r = (R) tradeAction.doBusiness(params);
            return r;
        } catch (Exception e) {
            throw e;
        }
    }
}

