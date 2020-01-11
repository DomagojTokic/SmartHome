package hr.fer.zemris.dipl.model.actions.concrete;

import hr.fer.zemris.dipl.model.actions.TemporalAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.Semaphore;

/**
 * Created by Domagoj on 1.6.2017..
 */
public class TemperatureAction extends TemporalAction {
	
	public TemperatureAction(Double multiplier, Double multiplierInc, AbstractAppliance appliance) {
		name = new SimpleStringProperty("Temperature action");
		weight = homeState.getTemperature();
		this.multiplier = multiplier;
		this.multiplierInc = multiplierInc;
		this.appliance = appliance;
	}
	
	@Override
	public void executeAction() throws InterruptedException {
		homeState.setTemperatureWeight(weight);
		homeState.setTemperatureMultiplier(multiplier);
		homeState.setTemperatureMultiplierInc(multiplierInc);
	}
	
	@Override
	public Double getMinValue() {
		return homeState.getMinTemperature();
	}
	
	@Override
	public Double getMaxValue() {
		return homeState.getMaxTemperature();
	}
	
	@Override
	public Double getStepValue() {
		return homeState.getStepTemperature();
	}
	
	@Override
	protected Semaphore getStartSemaphore() {
		return homeState.getTemperatureSemaphore();
	}
	
	@Override
	protected Semaphore getEndSemaphore() {
		return homeState.getTemperatureEndSemaphore();
	}
}
