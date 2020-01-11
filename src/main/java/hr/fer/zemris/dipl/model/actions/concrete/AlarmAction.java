package hr.fer.zemris.dipl.model.actions.concrete;

import hr.fer.zemris.dipl.model.actions.InstantAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 5.6.2017..
 */
public class AlarmAction extends InstantAction {
	
	public AlarmAction(AbstractAppliance appliance) {
		name = new SimpleStringProperty("Alarm action");
		this.appliance = appliance;
	}
	
	@Override
	public void executeAction() {
		homeState.setAlarm(state);
	}
}
