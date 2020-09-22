package com.pc.dubboconsumer.controller;


import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.pc.dubboapi.serviceapi.CallService;
import com.pc.dubboapi.serviceapi.TradeService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CallController {

    @Reference(loadbalance="random",retries = -1)
    private CallService callService;


    @Reference
    private TradeService tradeService;


    //http://localhost:8082/call
    @RequestMapping("/call")
    public String call(int num) {

        int count = 0;
        for(int i=0;i<num;i++) {
            try {
                System.out.println(callService.call());
            } catch (Exception e) {
//                System.out.println(callService.fallback());
                count++;
                System.err.println("Flow Blocked");
            }

        }
        return "blocked"+count;
    }



}
