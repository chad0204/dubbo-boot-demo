package com.pc.dubboprovider.excutor;


import com.pc.dubboapi.request.AbstractTradeRequest;
import com.pc.dubboapi.response.TradeResponse;
import com.pc.dubboprovider.action.param.PayParam;
import com.pc.dubboprovider.action.param.WeixinPayParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


/**
 *
 * @author pengchao
 * @date 18:00 2020-09-18
 */
@Component
public class WeixinPayTradeExecutor extends AbstractTradeExecutor<AbstractTradeRequest, TradeResponse> {


    @Override
    protected PayParam getParam(AbstractTradeRequest request) {
        WeixinPayParam weixinPayParam = new WeixinPayParam();
        BeanUtils.copyProperties(request,weixinPayParam);
        return weixinPayParam;
    }
}
