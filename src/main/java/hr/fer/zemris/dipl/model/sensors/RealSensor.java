package hr.fer.zemris.dipl.model.sensors;

/**
 * Created by Domagoj on 19.6.2017..
 */
public abstract class RealSensor extends AbstractSensor {
	
	protected abstract void disableDevice();
	
	protected abstract void enableDevice();
	
	protected abstract void setUpDevice();
	
	@Override
	public void disable() {
		super.disable();
		disableDevice();
	}
	
	@Override
	public void enable() {
		enableDevice();
		setUpDevice();
		super.enable();
	}
}
