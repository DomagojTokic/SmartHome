package hr.fer.zemris.dipl.model.sensors.virtual;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.NumericValueSensor;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.Semaphore;

/**
 * Implementation of {@link NumericValueSensor} where value is Carbon dioxide concentration (percentage). 1.0 is 1%.
 */
public class CarbonDioxideSensor extends NumericValueSensor {
	
	public CarbonDioxideSensor(HomeState homeState) {
		name = new SimpleStringProperty("CO2 sensor");
		this.homeState = homeState;
		measurement = "Carbon dioxide";
		unit = "ppm";
		sensorEnum = SensorEnum.CARBON_DIOXIDE;
		ruleEnum = RuleEnum.CARBON_DIOXIDE;
	}
	
	@Override
	public double getCurrentValue() {
		return homeState.getCarbonDioxide();
	}
	
	@Override
	public void setCurrentValue(double value) {
		homeState.setCarbonDioxide(value);
	}
	
	@Override
	public double getWeightValue() {
		return homeState.getCarbonDioxideWeight();
	}
	
	@Override
	public void setWeightValue(double weightValue) {
		homeState.setCarbonDioxideWeight(weightValue);
	}
	
	@Override
	public double getMinValue() {
		return homeState.getMinCarbonDioxide();
	}
	
	@Override
	public double getMaxValue() {
		return homeState.getMaxCarbonDioxide();
	}
	
	@Override
	public double getStepValue() {
		return homeState.getStepCarbonDioxide();
	}
	
	@Override
	public double getMinChange() {
		return homeState.getMinCarbonDioxideChange();
	}
	
	@Override
	public void setMinChange(double minChange) {
		homeState.setMinCarbonDioxideChange(minChange);
	}
	
	@Override
	public double getMaxChange() {
		return homeState.getMaxCarbonDioxideChange();
	}
	
	@Override
	public void setMaxChange(double maxChange) {
		homeState.setMaxCarbonDioxideChange(maxChange);
	}
	
	@Override
	protected double getMultiplier() {
		return homeState.getCarbonDioxideChangeMultiplier();
	}
	
	@Override
	protected void setMultiplier(double multiplier) {
		homeState.setCarbonDioxideMultiplier(multiplier);
	}
	
	@Override
	protected double getMultiplierIncrement() {
		return homeState.getCarbonDioxideMultiplierInc();
	}
	
	@Override
	protected Semaphore getSemaphore() {
		return homeState.getCarbonDioxideSemaphore();
	}
	
	@Override
	protected Semaphore getEndSemaphore() {
		return homeState.getCarbonDioxideEndSemaphore();
	}
	
	@Override
	protected void resetParameters() {
		homeState.resetCarbonDioxideChangeParameters();
	}
}
