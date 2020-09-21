package com.pc.dubboprovider.service.impl;

import com.pc.dubboapi.request.AbstractTradeRequest;
import com.pc.dubboapi.request.TradeRequest;
import com.pc.dubboapi.response.TradeResponse;
import com.pc.dubboapi.serviceapi.TradeService;
import com.pc.tpt.core.TptClient;
import com.twodfire.share.result.Result;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pengchao
 * @date 17:42 2020-09-18
 */
@Service
public class TradeServiceImpl implements TradeService {


    @Autowired
    private TptClient tptClient;


    @Override
    public Result<TradeResponse> getTradeBill(TradeRequest request) {
        return tptClient.execute(request);
    }
}
