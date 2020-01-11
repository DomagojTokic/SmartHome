package hr.fer.zemris.dipl.model.actions.concrete;

import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 6.6.2017..
 */
public class HeatingAction extends TemperatureAction {
	
	public HeatingAction(Double multiplier, Double multiplierInc, AbstractAppliance appliance) {
		super(multiplier, multiplierInc, appliance);
		name = new SimpleStringProperty("Heating room");
	}
	
	@Override
	public void executeAction() throws InterruptedException {
		if (homeState.getTemperature() < weight) {
			super.executeAction();
		}
	}
}
