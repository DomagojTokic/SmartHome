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
@Rule(name = "Motion rule")
public class MotionRule extends BooleanRule {
	
	public MotionRule() {
		this.name = "Motion presence";
		ruleEnum = RuleEnum.MOTION;
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkBoolean(condition, homeState.getMotionDetected());
	}
	
	@Action
	public void noAction(){}
}
