package com.example.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public  static  void  testSentinel(){
		initFlowRules();
		int i=1;
		while (true) {
			Entry entry = null;
			try {
				entry = SphU.entry("HelloWorld");
				/*您的业务逻辑 - 开始*/
				System.out.println("hello world"+String.valueOf(i));
				i++;
				/*您的业务逻辑 - 结束*/
			} catch (BlockException e1) {
				/*流控逻辑处理 - 开始*/
				System.out.println("block!");
				/*流控逻辑处理 - 结束*/
			} finally {
				if (entry != null) {
					entry.exit();
				}
			}
		}
	}
	public static void initFlowRules(){
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule();
		rule.setResource("HelloWorld");
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		// Set limit QPS to 20.
		rule.setCount(1);
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
	}
}






