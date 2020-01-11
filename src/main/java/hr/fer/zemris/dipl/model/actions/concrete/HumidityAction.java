package hr.fer.zemris.dipl.model.actions.concrete;

import hr.fer.zemris.dipl.model.actions.TemporalAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.Semaphore;

/**
 * Created by Domagoj on 1.6.2017..
 */
public class HumidityAction extends TemporalAction {
	
	public HumidityAction(Double multiplier, Double multiplierInc, AbstractAppliance appliance) {
		name = new SimpleStringProperty("Humidity change");
		weight = homeState.getHumidity();
		this.multiplier = multiplier;
		this.multiplierInc = multiplierInc;
		this.appliance = appliance;
	}
	
	@Override
	public void executeAction() throws InterruptedException {
		homeState.setHumidityWeight(weight);
		homeState.setHumidityMultiplier(multiplier);
		homeState.setHumidityMultiplierInc(multiplierInc);
	}
	
	@Override
	public Double getMinValue() {
		return homeState.getMinHumidity();
	}
	
	@Override
	public Double getMaxValue() {
		return homeState.getMaxHumidity();
	}
	
	@Override
	public Double getStepValue() {
		return homeState.getStepHumidity();
	}
	
	@Override
	protected Semaphore getStartSemaphore() {
		return homeState.getHumiditySemaphore();
	}
	
	@Override
	protected Semaphore getEndSemaphore() {
		return homeState.getHumidityEndSemaphore();
	}
}
