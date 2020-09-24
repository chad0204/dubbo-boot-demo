package com.pc.dubboprovider.test;


import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wuxiaobo@didachuxing.com
 * @create 2018-11-26 22:28
 **/
public class TestSph0 {

    public static void main(String[] args) throws InterruptedException {
        //如果没有init，那么SphO不会生效
        initFlowRules();
        // 获取资源名
        while (true) {
            if (SphO.entry("HelloWorld")) {
                try {
                    //被保护的业务逻辑
                    System.out.println("HelloWorld");
                }finally {
                    SphO.exit();
                }
            } else {
                System.out.println("Blocked");
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<FlowRule>();

        // 定义规则
        FlowRule rule = new FlowRule();
        //定义资源
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 10.
        rule.setCount(10);
        //添加规则
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}



