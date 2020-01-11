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
@Rule(name = "Sound system rule")
public class SoundSystemRule extends BooleanRule {
	
	public SoundSystemRule() {
		this.name = "Sound system rule";
		ruleEnum = RuleEnum.SOUND_SYSTEM;
	}
	
	@Condition
	public boolean when() {
		return ConditionChecker.checkBoolean(condition, homeState.getLight());
	}
	
	@Action
	public void noAction(){}
	
}
