package com.pc.dubboprovider.test;


/**
 * 测试包依赖
 *
 * @author pengchao
 * @date 15:20 2020-09-10
 */

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wuxiaobo@didachuxing.com
 * @create 2018-11-11 13:54
 **/
public class TestSphU {

    public static void main(String[] args) throws InterruptedException {
        //如果没有init，那么SphU不会生效
        initFlowRules();

        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                System.out.println("hello world");//相当于业务逻辑
            } catch (BlockException e1) {
                // 资源访问阻止，被限流或者是降级
                // 进行相应的处理操作
                System.out.println("block!");//相当于fallback

                //熔断器打开后 休眠一下 等下一秒继续
                TimeUnit.SECONDS.sleep(1);
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<FlowRule>();
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



