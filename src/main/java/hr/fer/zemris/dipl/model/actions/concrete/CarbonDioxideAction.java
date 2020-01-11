package hr.fer.zemris.dipl.model.actions.concrete;


import hr.fer.zemris.dipl.model.actions.TemporalAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.Semaphore;

/**
 * Created by Domagoj on 1.6.2017..
 */
public class CarbonDioxideAction extends TemporalAction {
	
	public CarbonDioxideAction(Double multiplier, Double multiplierInc, AbstractAppliance appliance) {
		name = new SimpleStringProperty("CarbonDioxide action");
		weight = homeState.getCarbonDioxide();
		this.multiplier = multiplier;
		this.multiplierInc = multiplierInc;
		this.appliance = appliance;
	}
	
	@Override
	public void executeAction() throws InterruptedException {
		homeState.setCarbonDioxideWeight(weight);
		homeState.setCarbonDioxideMultiplier(multiplier);
		homeState.setCarbonDioxideMultiplierInc(multiplierInc);
	}
	
	@Override
	public Double getMinValue() {
		return homeState.getMinCarbonDioxide();
	}
	
	@Override
	public Double getMaxValue() {
		return homeState.getMaxCarbonDioxide();
	}
	
	@Override
	public Double getStepValue() {
		return homeState.getStepCarbonDioxide();
	}
	
	@Override
	protected Semaphore getStartSemaphore() {
		return homeState.getCarbonDioxideSemaphore();
	}
	
	@Override
	protected Semaphore getEndSemaphore() {
		return homeState.getCarbonDioxideEndSemaphore();
	}
}
