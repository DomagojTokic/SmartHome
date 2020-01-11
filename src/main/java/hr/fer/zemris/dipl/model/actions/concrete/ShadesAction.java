package hr.fer.zemris.dipl.model.actions.concrete;

import hr.fer.zemris.dipl.model.actions.InstantAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 5.6.2017..
 */
public class ShadesAction extends InstantAction {
	
	public ShadesAction(AbstractAppliance appliance) {
		name = new SimpleStringProperty("Shades on/off");
		this.appliance = appliance;
	}
	
	@Override
	public void executeAction() {
		homeState.setShades(state);
	}
}
