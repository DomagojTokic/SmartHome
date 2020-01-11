package hr.fer.zemris.dipl.model.sensors;

/**
 * Simulates sensor where existing values are binary - true and false. It has only one parameter and it's the chance
 * of sensor changing the value which it's reading.
 * P(!value) = changeChance
 */
public abstract class BooleanValueSensor extends AbstractSensor {
	
	public abstract double getChangeChance();
	
	public abstract void setState(boolean state);
	
	public abstract boolean getCurrentState();
	
	public abstract void setChangeChance(double changeChance);
	
	@Override
	public void nextValue() {
		if (random.nextDouble() < getChangeChance()) {
			setState(!getCurrentState());
		}
	}
}
