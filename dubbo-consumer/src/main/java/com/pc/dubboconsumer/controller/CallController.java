package com.pc.dubboconsumer.controller;


import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.pc.dubboapi.serviceapi.CallService;
import com.pc.dubboapi.serviceapi.TradeService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


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



}
