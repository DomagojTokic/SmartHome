package hr.fer.zemris.dipl.model.rules.concrete;

import hr.fer.zemris.dipl.model.rules.BooleanRule;
import hr.fer.zemris.dipl.model.rules.conditions.ConditionChecker;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

/**
 * Created by Domagoj on 5.6.2017..
 */
@Rule(name = "Lights rule")
public class LightRule extends BooleanRule {
	
	public LightRule() {
		this.name = "Lights (appliance) rule";
		ruleEnum = RuleEnum.LIGHT;
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkBoolean(condition, homeState.getLight());
	}
	
	@Action
	public void noAction(){}
	
}
