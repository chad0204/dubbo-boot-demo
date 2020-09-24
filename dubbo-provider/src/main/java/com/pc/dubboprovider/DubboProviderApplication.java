package com.pc.dubboprovider;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.pc.dubboapi.serviceapi.CallService;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
@DubboComponentScan(basePackages = "com.pc.dubboprovider.service.impl")
@SpringBootApplication(scanBasePackages = {"com.pc.tpt","com.pc.dubboprovider"})
public class DubboProviderApplication {

    //对dubbo接口进行限流熔断（如果要对其他方式进行熔断，需要自定义）,SentinelDubboProviderFilter
    private static final String RESOURCE_INTERFACE_KEY = CallService.class.getName();
    private static final String RESOURCE_METHOD_KEY = RESOURCE_INTERFACE_KEY+":call()";

    public static void main(String[] args) {
//        initFlowRule(10,false);//初始化限流规则,接口限流
        initFlowRule(5,true);//初始化限流规则,方法限流

        registryFallBack();

        SpringApplication.run(DubboProviderApplication.class, args);
    }

    private static void registryFallBack() {
        //注册限流后的降级方法 返回默认结果
//        DubboFallbackRegistry.setProviderFallback(new DubboFallback() {
//            @Override
//            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException e) {
//                return AsyncRpcResult.newDefaultAsyncResult("flow fallback",invocation);
//            }
//        });

        //注册降级 返回自定义异常,返回异常使consumer端发生熔断
        DubboFallbackRegistry.setProviderFallback(new DubboFallback() {
            @Override
            public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
                return  AsyncRpcResult.newDefaultAsyncResult(new RuntimeException("flow fallback"), invocation);
            }
        });
    }

    //限流配置
    private static void initFlowRule(int interfaceFlowLimit, boolean method) {
        FlowRule flowRule = new FlowRule(RESOURCE_INTERFACE_KEY)
                .setCount(interfaceFlowLimit)
                .setGrade(RuleConstant.FLOW_GRADE_QPS);//限流阈值类型（QPS 或并发线程数）
        List<FlowRule> list = new ArrayList<>();
        if(method) {
            //针对具体的方法限流
            FlowRule flowRule1 = new FlowRule(RESOURCE_METHOD_KEY)
                    .setCount(5)//限流阈值 qps=5
                    .setGrade(RuleConstant.FLOW_GRADE_QPS);

            list.add(flowRule1);
        }
        list.add(flowRule);
//        flowRule.setLimitApp("default");//流控针对的调用来源，若为 default 则不区分调用来源
        // 流量控制手段（直接拒绝、Warm Up、匀速排队）
//        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        FlowRuleManager.loadRules(list);
    }


}
