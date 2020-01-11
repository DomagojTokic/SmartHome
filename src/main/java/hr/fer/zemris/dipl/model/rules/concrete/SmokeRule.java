package hr.fer.zemris.dipl.model.rules.concrete;

import hr.fer.zemris.dipl.model.rules.BooleanRule;
import hr.fer.zemris.dipl.model.rules.conditions.ConditionChecker;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

/**
 * Created by Domagoj on 25.4.2017..
 */
@Rule(name = "Smoke rule")
public class SmokeRule extends BooleanRule {
	
	public SmokeRule() {
		this.name = "Smoke presence";
		ruleEnum = RuleEnum.SMOKE;
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkBoolean(condition, homeState.getSmokeDetected());
	}
	
	@Action
	public void noAction(){}
}
