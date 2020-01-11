package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.SoundSystemAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 5.6.2017..
 */
public class SoundSystem extends AbstractAppliance {
	
	public SoundSystem() {
		name = new SimpleStringProperty("Sound System");
		applianceEnum = ApplianceEnum.SOUND_SYSTEM;
		ruleEnum = RuleEnum.SOUND_SYSTEM;
		actions.add(new SoundSystemAction(this));
	}
	
	@Override
	public Boolean getApplianceState() {
		return homeState.getSoundSystemState();
	}
}
