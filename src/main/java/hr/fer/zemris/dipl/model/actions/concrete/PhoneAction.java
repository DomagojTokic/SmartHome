package hr.fer.zemris.dipl.model.actions.concrete;

import hr.fer.zemris.dipl.model.actions.InstantAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 5.6.2017..
 */
public class PhoneAction extends InstantAction {
	
	public  PhoneAction(AbstractAppliance appliance) {
		name = new SimpleStringProperty("Phone action");
		this.appliance = appliance;
	}
	
	@Override
	public void executeAction() {
		homeState.setPhone(state);
	}
}
