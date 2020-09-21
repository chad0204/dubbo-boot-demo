package com.pc.dubboprovider.excutor;

import com.pc.dubboapi.request.AbstractTradeRequest;
import com.pc.dubboapi.response.TradeResponse;
import com.pc.dubboprovider.action.PayResult;
import com.pc.dubboprovider.action.TradeActionClient;
import com.pc.dubboprovider.action.param.PayParam;
import com.pc.dubboprovider.result.WeixinPayResult;
import com.pc.dubboprovider.util.CopyUtil;
import com.twodfire.share.result.Result;
import com.twodfire.share.util.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pengchao
 * @date 14:13 2020-09-21
 */
public abstract class AbstractTradeExecutor<RSQ extends AbstractTradeRequest,RSP extends TradeResponse> extends AbstractExecutor<RSQ,RSP> {

    @Autowired
    TradeActionClient tradeActionClient;


    @Override
    protected Result<RSP> _execute(RSQ request) {
        PayParam param = getParam(request);
        PayResult result = tradeActionClient.doBusiness(param);
        TradeResponse response = new TradeResponse();
        response.setType(result.getType());
        response.setFee(result.getFee());

        CopyUtil.copyProperties(response,result);
        return ResultUtil.successResult(response);
    }

    protected abstract PayParam getParam(RSQ request);


}
