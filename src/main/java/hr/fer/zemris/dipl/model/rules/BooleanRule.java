package hr.fer.zemris.dipl.model.rules;

import hr.fer.zemris.dipl.model.rules.conditions.BooleanCondition;

/**
 * Created by Domagoj on 2.5.2017..
 */
public abstract class BooleanRule extends AbstractRule {
	
	protected BooleanCondition condition;
	
	public BooleanRule() {
		valueType = ValueType.BOOLEAN;
	}
	
	public BooleanCondition getCondition() {
		return condition;
	}
	
	public void setCondition(BooleanCondition condition) {
		this.condition = condition;
	}
	
	@Override
	public String getConditionString() {
		return condition.toString();
	}
}
