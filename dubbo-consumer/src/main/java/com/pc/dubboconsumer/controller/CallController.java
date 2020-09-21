package com.pc.dubboconsumer.controller;


import com.pc.dubboapi.request.AbstractTradeRequest;
import com.pc.dubboapi.request.AliPayTradeRequest;
import com.pc.dubboapi.request.WeixinPayTradeRequest;
import com.pc.dubboapi.response.TradeResponse;
import com.pc.dubboapi.serviceapi.CallService;
import com.pc.dubboapi.serviceapi.TradeService;
import com.twodfire.share.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@RestController
public class CallController {

    @Reference(loadbalance="random")
    private CallService callService;


    @Reference
    private TradeService tradeService;


    //http://localhost:8082/call
    @RequestMapping("/call")
    public String call() {
        return callService.call();
    }

    @PostConstruct
    public void init() {
        System.out.println(callService.call());

        WeixinPayTradeRequest weixin = new WeixinPayTradeRequest();
        Result<TradeResponse> result = tradeService.getTradeBill(weixin);
        TradeResponse response = result.getModel();
        System.out.println(response.toString());

        AliPayTradeRequest aliPay = new AliPayTradeRequest();
        System.out.println(tradeService.getTradeBill(aliPay).getModel().toString());

    }

}
