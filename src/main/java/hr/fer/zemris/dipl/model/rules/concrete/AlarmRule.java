package hr.fer.zemris.dipl.model.rules.concrete;

import hr.fer.zemris.dipl.model.rules.BooleanRule;
import hr.fer.zemris.dipl.model.rules.conditions.ConditionChecker;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

/**
 * Created by Domagoj on 12.6.2017..
 */
@Rule(name = "Alarm rule")
public class AlarmRule extends BooleanRule {
	
	public AlarmRule() {
		this.name = "Alarm (appliance) rule";
		ruleEnum = RuleEnum.ALARM;
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkBoolean(condition, homeState.getAlarm());
	}
	
	@Action
	public void noAction(){}
}
