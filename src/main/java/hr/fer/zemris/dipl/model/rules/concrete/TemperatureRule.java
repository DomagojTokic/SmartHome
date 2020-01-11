package hr.fer.zemris.dipl.model.rules.concrete;

import hr.fer.zemris.dipl.model.rules.NumericRule;
import hr.fer.zemris.dipl.model.rules.conditions.ConditionChecker;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

/**
 * Created by Domagoj on 23.4.2017..
 */
@Rule(name = "Temperature rule")
public class TemperatureRule extends NumericRule {
	
	public TemperatureRule() {
		this.name = "Temperature (°C)";
		ruleEnum = RuleEnum.TEMPERATURE;
		value = homeState.getTemperatureWeight();
	}
	
	@Override
	public Double getMinValue() {
		return homeState.getMinTemperature();
	}
	
	@Override
	public Double getMaxValue() {
		return homeState.getMaxTemperature();
	}
	
	@Override
	public Double getStepValue() {
		return homeState.getStepTemperature();
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkNumeric(condition, homeState.getTemperature(), value);
	}
	
	@Action
	public void noAction(){}
}
