package hr.fer.zemris.dipl.model.rules.concrete;

import hr.fer.zemris.dipl.model.rules.NumericRule;
import hr.fer.zemris.dipl.model.rules.conditions.ConditionChecker;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

/**
 * Created by Domagoj on 25.4.2017..
 */
@Rule(name = "Moisture rule")
public class HumidityRule extends NumericRule {
	
	public HumidityRule() {
		this.name = "Humidity (relative) percentage";
		ruleEnum = RuleEnum.HUMIDITY;
		value = homeState.getHumidityWeight();
	}
	
	@Override
	public Double getMinValue() {
		return homeState.getMinHumidity();
	}
	
	@Override
	public Double getMaxValue() {
		return homeState.getMaxHumidity();
	}
	
	@Override
	public Double getStepValue() {
		return homeState.getStepHumidity();
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkNumeric(condition, homeState.getHumidity(), value);
	}
	
	@Action
	public void noAction(){}
}
