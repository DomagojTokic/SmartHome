package hr.fer.zemris.dipl.model.sensors;

import java.util.concurrent.Semaphore;

/**
 * Simulates change of numeric value homeState in room by going from current homeState of room to value of weight value. If
 * multiplier is not 1 and multiplier increment is not 0, it's considered that an appliance is used for
 * changing temperature. When a goal of changing room state with appliance is achieved, multiplier and it's increment
 * will be set to beginning values to simulate the end of appliance action. Formula for next iteration of
 * implementation of NumericValueSensor process is
 * V(i+1) = V(i) + N ∈ [minChange, maxChange] * M(i)
 * M(i+i) = M(i) + MI
 * V - value
 * M - multiplier
 * MI - multiplier increment
 */
public abstract class NumericValueSensor extends AbstractSensor {
	
	protected String unit;
	
	public abstract double getCurrentValue();
	
	public abstract void setCurrentValue(double value);
	
	public abstract double getWeightValue();
	
	public abstract void setWeightValue(double weightValue);
	
	public abstract double getMinValue();
	
	public abstract double getMaxValue();
	
	public abstract double getStepValue();
	
	public abstract double getMinChange();
	
	public abstract void setMinChange(double minChange);
	
	public abstract double getMaxChange();
	
	public abstract void setMaxChange(double maxChange);
	
	protected abstract double getMultiplier();
	
	protected abstract void setMultiplier(double multiplier);
	
	protected abstract double getMultiplierIncrement();
	
	protected abstract Semaphore getSemaphore();
	
	protected abstract Semaphore getEndSemaphore();
	
	protected abstract void resetParameters();
	
	public String getUnit() {
		return unit;
	}
	
	@Override
	public void nextValue() throws InterruptedException {
		double currentValue = getCurrentValue();
		double weightValue = getWeightValue();
		
		double nextValue;
		double valueChange = (getMinChange() + random.nextDouble() * (getMaxChange() - getMinChange()));
		if (weightValue - currentValue > 0) {
			nextValue = currentValue + getMultiplier() * valueChange;
		} else {
			nextValue = currentValue - getMultiplier() * valueChange;
		}
		
		setMultiplier(getMultiplier() + getMultiplierIncrement());
		
		if ((currentValue < weightValue && nextValue > weightValue || currentValue > weightValue && nextValue <
				weightValue) && getSemaphore().availablePermits() == 0) {
			getEndSemaphore().release();
			resetParameters();
			getSemaphore().release();
		}
		
		setCurrentValue(nextValue);
	}
}
