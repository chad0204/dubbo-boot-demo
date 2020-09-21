package com.pc.dubboprovider.action.action;

import com.pc.dubboprovider.action.PayResult;
import com.pc.dubboprovider.action.TradeAction;
import com.pc.dubboprovider.action.param.PayParam;
import com.pc.dubboprovider.result.WeixinPayResult;
import org.springframework.stereotype.Component;

/**
 *
 * @author pengchao
 * @date 14:19 2020-09-21
 */
@Component
public class WeixinPayAction implements TradeAction<PayParam, PayResult> {

    /**
     * 执行业务
     *
     * @param payParam
     * @return
     */
    @Override
    public PayResult doBusiness(PayParam payParam) {
        WeixinPayResult weixinPayResult = new WeixinPayResult();
        weixinPayResult.setFee(999.0);
        weixinPayResult.setType("微信支付");
        return weixinPayResult;
    }
}
