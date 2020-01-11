package hr.fer.zemris.dipl.model.rules;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.interfaces.TableViewEditable;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;

import java.io.Serializable;

/**
 * Created by Domagoj on 2.5.2017..
 */
public abstract class AbstractRule  extends TableViewEditable implements Cloneable, Serializable {
	
	protected String name;
	protected RuleEnum ruleEnum;
	protected transient HomeState homeState;
	
	public String getName() {
		return name;
	}
	
	public void setHomeState(HomeState homeState) {
		this.homeState = homeState;
	}
	
	public abstract String getConditionString();
	
	public RuleEnum getRuleEnum() {
		return ruleEnum;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
