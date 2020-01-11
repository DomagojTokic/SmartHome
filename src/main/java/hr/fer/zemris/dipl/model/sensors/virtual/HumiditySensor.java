package hr.fer.zemris.dipl.model.sensors.virtual;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.NumericValueSensor;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.Semaphore;

/**
 * Implementation of {@link NumericValueSensor} where value is humidity (percentage).  1.0 is 1%.
 */
public class HumiditySensor extends NumericValueSensor {
	
	public HumiditySensor(HomeState homeState) {
		name = new SimpleStringProperty("Humidity sensor");
		this.homeState = homeState;
		measurement = "Humidity";
		unit = "%";
		sensorEnum = SensorEnum.HUMIDITY;
		ruleEnum = RuleEnum.HUMIDITY;
	}
	
	@Override
	public double getCurrentValue() {
		return homeState.getHumidity();
	}
	
	@Override
	public void setCurrentValue(double value) {
		homeState.setHumidity(value);
	}
	
	@Override
	public double getWeightValue() {
		return homeState.getHumidityWeight();
	}
	
	@Override
	public void setWeightValue(double weightValue) {
		homeState.setHumidityWeight(weightValue);
	}
	
	@Override
	public double getMinValue() {
		return homeState.getMinHumidity();
	}
	
	@Override
	public double getMaxValue() {
		return homeState.getMaxHumidity();
	}
	
	@Override
	public double getStepValue() {
		return homeState.getStepHumidity();
	}
	
	@Override
	public double getMinChange() {
		return homeState.getMinHumidityChange();
	}
	
	@Override
	public void setMinChange(double minChange) {
		homeState.setMinHumidityChange(minChange);
	}
	
	@Override
	public double getMaxChange() {
		return homeState.getMaxHumidityChange();
	}
	
	@Override
	public void setMaxChange(double maxChange) {
		homeState.setMaxHumidityChange(maxChange);
	}
	
	@Override
	protected double getMultiplier() {
		return homeState.getHumidityMultiplier();
	}
	
	@Override
	protected void setMultiplier(double multiplier) {
		homeState.setHumidityMultiplier(multiplier);
	}
	
	@Override
	protected double getMultiplierIncrement() {
		return homeState.getHumidityMultiplierInc();
	}
	
	@Override
	protected Semaphore getSemaphore() {
		return homeState.getHumiditySemaphore();
	}
	
	@Override
	protected Semaphore getEndSemaphore() {
		return homeState.getHumidityEndSemaphore();
	}
	
	@Override
	protected void resetParameters() {
		homeState.resetHumidityChangeParameters();
	}
}
