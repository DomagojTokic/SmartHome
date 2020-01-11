package hr.fer.zemris.dipl.model.rules;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.rules.conditions.NumericCondition;

/**
 * Created by Domagoj on 2.5.2017..
 */
public abstract class NumericRule extends AbstractRule {
	
	protected NumericCondition condition;
	protected double value;
	
	public NumericRule() {
		valueType = ValueType.DOUBLE;
		homeState = new HomeState();
	}
	
	public abstract Double getMinValue();
	public abstract Double getMaxValue();
	public abstract Double getStepValue();
	
	public NumericCondition getCondition() {
		return condition;
	}
	
	public void setCondition(NumericCondition condition) {
		this.condition = condition;
	}
	
	@Override
	public String getConditionString() {
		return condition + " " + value;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
