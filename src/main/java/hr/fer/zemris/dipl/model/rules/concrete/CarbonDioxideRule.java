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
@Rule(name = "Carbon Dioxide rule")
public class CarbonDioxideRule extends NumericRule {
	
	public CarbonDioxideRule() {
		this.name = "Carbon Dioxide concentration";
		ruleEnum = RuleEnum.CARBON_DIOXIDE;
		value = homeState.getCarbonDioxideWeight();
	}
	
	@Override
	public Double getMinValue() {
		return homeState.getMinCarbonDioxide();
	}
	
	@Override
	public Double getMaxValue() {
		return homeState.getMaxCarbonDioxide();
	}
	
	@Override
	public Double getStepValue() {
		return homeState.getStepCarbonDioxide();
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkNumeric(condition, homeState.getCarbonDioxide(), value);
	}
	
	@Action
	public void noAction(){}
}
