package com.pc.dubboconsumer;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.pc.dubboapi.serviceapi.CallService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;


@EnableDubboConfig
@SpringBootApplication
public class DubboConsumerApplication {


    private static final String RESOURCE_INTERFACE_KEY = CallService.class.getName();
    private static final String RESOURCE_METHOD_KEY = RESOURCE_INTERFACE_KEY+":call()";

    public static void main(String[] args) {
        initdegradeRule(10);//初始化降级规则
        SpringApplication.run(DubboConsumerApplication.class, args);
    }


    private static void initdegradeRule(int timewindow) {
        //同一个资源可以同时有多个降级规则
        DegradeRule degradeRule = new DegradeRule(RESOURCE_INTERFACE_KEY)
                //DEGRADE_GRADE_RT 平均响应时间，count以ms为单位，当出现响应时间超过count，那么进入准降级状态，如果接下来的5个请求都超过count，那么在timeWindow内，抛出DegradeException
                //DEGRADE_GRADE_EXCEPTION_RATIO 异常比例，每秒 异常数/通过数 超过count,那么自动返回抛出DegradeException
                //DEGRADE_GRADE_EXCEPTION_COUNT 异常数,当资源近1分钟的异常数目超过阈值之后会进行熔断
                .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT)
                //响应时间(ms)、异常比例、异常数
                .setCount(5)
                //时间窗口(s)
                .setTimeWindow(timewindow)
                //熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断
                .setMinRequestAmount(1);

        List<DegradeRule> rules = new ArrayList<>();
        rules.add(degradeRule);
        DegradeRuleManager.loadRules(rules);

    }

}
