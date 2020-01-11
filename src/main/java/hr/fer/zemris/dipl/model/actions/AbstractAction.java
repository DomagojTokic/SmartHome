package hr.fer.zemris.dipl.model.actions;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.interfaces.IProcess;
import hr.fer.zemris.dipl.model.interfaces.TableViewEditable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by Domagoj on 5.6.2017..
 */
public abstract class AbstractAction extends TableViewEditable implements IProcess, Cloneable, Serializable {
	
	protected StringProperty name;
	protected AbstractAppliance appliance;
	protected transient HomeState homeState;
	
	public abstract void executeAction() throws InterruptedException;
	
	public abstract String getValueString();
	
	public String getName() {
		return name.get();
	}
	
	public AbstractAppliance getAppliance() {
		return appliance;
	}
	
	public void setHomeState(HomeState homeState) {
		this.homeState = homeState;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public void setAppliance(AbstractAppliance appliance) {
		this.appliance = appliance;
	}
}
