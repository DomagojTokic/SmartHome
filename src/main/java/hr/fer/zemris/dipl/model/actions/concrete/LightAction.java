package hr.fer.zemris.dipl.model.actions.concrete;

import hr.fer.zemris.dipl.model.actions.InstantAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 5.6.2017..
 */
public class LightAction extends InstantAction {
	
	public LightAction(AbstractAppliance appliance) {
		name = new SimpleStringProperty("Lights on/off");
		this.appliance = appliance;
	}
	
	@Override
	public void executeAction() {
		homeState.setLight(state);
	}
}
