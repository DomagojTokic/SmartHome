package hr.fer.zemris.dipl.model.sensors.virtual;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.NumericValueSensor;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.Semaphore;

/**
 * Implementation of {@link NumericValueSensor} where value is temperature.
 */
public class TemperatureSensor extends NumericValueSensor {
	
	public TemperatureSensor(HomeState homeState) {
		name = new SimpleStringProperty("Temperature sensor");
		this.homeState = homeState;
		measurement = "Temperature";
		unit = "°C";
		sensorEnum = SensorEnum.TEMPERATURE;
		ruleEnum = RuleEnum.TEMPERATURE;
	}
	
	@Override
	public double getCurrentValue() {
		return homeState.getTemperature();
	}
	
	@Override
	public void setCurrentValue(double value) {
		homeState.setTemperature(value);
	}
	
	@Override
	public double getWeightValue() {
		return homeState.getTemperatureWeight();
	}
	
	@Override
	public void setWeightValue(double weightValue) {
		homeState.setTemperatureWeight(weightValue);
	}
	
	@Override
	public double getMinValue() {
		return homeState.getMinTemperature();
	}
	
	@Override
	public double getMaxValue() {
		return homeState.getMaxTemperature();
	}
	
	@Override
	public double getStepValue() {
		return homeState.getStepTemperature();
	}
	
	@Override
	public double getMinChange() {
		return homeState.getMinTemperatureChange();
	}
	
	@Override
	public void setMinChange(double minChange) {
		homeState.setMinTemperatureChange(minChange);
	}
	
	@Override
	public double getMaxChange() {
		return homeState.getMaxTemperatureChange();
	}
	
	@Override
	public void setMaxChange(double maxChange) {
		homeState.setMaxTemperatureChange(maxChange);
	}
	
	@Override
	protected double getMultiplier() {
		return homeState.getTemperatureMultiplier();
	}
	
	@Override
	protected void setMultiplier(double multiplier) {
		homeState.setTemperatureMultiplier(multiplier);
	}
	
	@Override
	protected double getMultiplierIncrement() {
		return homeState.getTemperatureMultiplierInc();
	}
	
	@Override
	protected Semaphore getSemaphore() {
		return homeState.getTemperatureSemaphore();
	}
	
	@Override
	protected Semaphore getEndSemaphore() {
		return homeState.getTemperatureEndSemaphore();
	}
	
	@Override
	protected void resetParameters() {
		homeState.resetTemperatureChangeParameters();
	}
}
