package com.pc.dubboprovider.action.action;

import com.pc.dubboprovider.action.PayResult;
import com.pc.dubboprovider.action.TradeAction;
import com.pc.dubboprovider.action.param.PayParam;
import com.pc.dubboprovider.result.AliPayResult;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author pengchao
 * @date 14:19 2020-09-21
 */
@Component
public class AliPayAction implements TradeAction<PayParam,PayResult> {



    /**
     * 执行业务
     *
     * @param
     * @return
     */
    @Override
    public PayResult doBusiness(PayParam payParam) {
        AliPayResult aliPayResult = new AliPayResult();
        aliPayResult.setFee(1000.0);
        aliPayResult.setType("支付宝支付");
        return aliPayResult;
    }


}
