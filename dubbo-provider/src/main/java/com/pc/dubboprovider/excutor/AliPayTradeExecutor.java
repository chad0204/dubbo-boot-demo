package com.pc.dubboprovider.excutor;


import com.pc.dubboapi.request.AbstractTradeRequest;
import com.pc.dubboapi.response.TradeResponse;
import com.pc.dubboprovider.action.param.AliPayParam;
import com.pc.dubboprovider.action.param.PayParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


/**
 *
 * @author pengchao
 * @date 18:00 2020-09-18
 */
@Component
public class AliPayTradeExecutor extends AbstractTradeExecutor<AbstractTradeRequest, TradeResponse> {


    @Override
    protected PayParam getParam(AbstractTradeRequest request) {
        AliPayParam aliPayParam = new AliPayParam();
        BeanUtils.copyProperties(request,aliPayParam);
        return aliPayParam;
    }
}
