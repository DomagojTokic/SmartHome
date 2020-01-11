package hr.fer.zemris.dipl.model.appliances;

/**
 * Created by Domagoj on 7.7.2017..
 */
public abstract class RealAppliance extends AbstractAppliance {
	
	protected abstract void disableDevice();
	
	protected abstract void enableDevice();
	
	protected abstract void setUpDevice();
}
