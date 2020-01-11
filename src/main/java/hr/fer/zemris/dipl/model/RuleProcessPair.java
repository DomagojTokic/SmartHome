package hr.fer.zemris.dipl.model;

import java.io.Serializable;

/**
 * Created by Domagoj on 4.5.2017..
 */
public class RuleProcessPair implements Serializable {
	
	private HomeRule rule;
	private HomeProcess process;
	
	public RuleProcessPair(HomeRule rule, HomeProcess process) {
		this.rule = rule;
		this.process = process;
	}
	
	public HomeRule getRule() {
		return rule;
	}
	
	public void setRule(HomeRule rule) {
		this.rule = rule;
	}
	
	public HomeProcess getProcess() {
		return process;
	}
	
	public void setProcess(HomeProcess process) {
		this.process = process;
		rule.setProcess(process);
	}
	
	public void setHomeState(HomeState homeState) {
		rule.setHomeState(homeState);
		process.setHomeState(homeState);
	}
}
