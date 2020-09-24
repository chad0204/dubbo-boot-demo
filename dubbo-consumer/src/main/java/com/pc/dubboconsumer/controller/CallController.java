package com.pc.dubboconsumer.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.pc.dubboapi.request.AliPayTradeRequest;
import com.pc.dubboapi.request.WeixinPayTradeRequest;
import com.pc.dubboapi.response.TradeResponse;
import com.pc.dubboapi.serviceapi.CallService;
import com.pc.dubboapi.serviceapi.TradeService;
import com.twodfire.share.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;


@RestController
public class CallController {

    @Reference(loadbalance="random",retries = -1)
    private CallService callService;


    @Reference
    private TradeService tradeService;


    /**
     *
     * provider进行限流
     * consumer进行熔断
     *
     * 当请求超过限流qps，provider返回fallback异常，如果异常超过consumer熔断阈值count，consumer将开启熔断，不发起远程调用直接返回fallback
     *
     * @param num 请求次数
     * @return
     * @throws InterruptedException
     */
    //http://localhost:8082/call
    @RequestMapping("/call")
    public String call(int num) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(num);

        for(int i=0;i<num;i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(callService.call());
                    } catch (Exception e) {
                        count.incrementAndGet();
                        System.err.println(e.getMessage());
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();
        return "blocked"+count.get();
    }




    @RequestMapping("/trade")
    public void init() {
        WeixinPayTradeRequest weixin = new WeixinPayTradeRequest();
        Result<TradeResponse> result = tradeService.getTradeBill(weixin);
        TradeResponse response = result.getModel();
        System.out.println(response.toString());

        AliPayTradeRequest aliPay = new AliPayTradeRequest();
        System.out.println(tradeService.getTradeBill(aliPay).getModel().toString());
    }


    /**
     * 测一测非 rpc接口
     *
     * 根据类名加方法名是不可以的
     * @param num
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/test")
    public String test(int num) throws InterruptedException {

        initFlowRule(5);

        for(int i=0;i<num;i++) {
            Entry entry = null;
            try {
                entry = SphU.entry(CallController.class.getName()+":test()");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        test();
                    }
                }).start();
            } catch (BlockException e) {
                System.err.println(e.getMessage());
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }

        return "blocked";
    }


    public void test() {

        System.out.println("success");

    }



    //限流配置
    private static void initFlowRule(int interfaceFlowLimit) {
        FlowRule flowRule = new FlowRule(CallController.class.getName()+":test()")
                .setCount(interfaceFlowLimit)
                .setGrade(RuleConstant.FLOW_GRADE_QPS);//限流阈值类型（QPS 或并发线程数）
        List<FlowRule> list = new ArrayList<>();
        list.add(flowRule);
        FlowRuleManager.loadRules(list);
    }


}
