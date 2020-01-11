package hr.fer.zemris.dipl.model.appliances;

import hr.fer.zemris.dipl.gui.observers.IObserver;
import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.interfaces.Subject;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Domagoj on 25.4.2017..
 */
public abstract class AbstractAppliance implements Subject, Cloneable, Serializable {

    protected StringProperty name;
    
    protected Boolean applianceState;
	
	protected ApplianceEnum applianceEnum;
	
	protected RuleEnum ruleEnum;
    
    protected transient HomeState homeState;

    protected transient List<AbstractAction> actions = new ArrayList<>();
	
	private transient List<IObserver> observers = new ArrayList<>();

    public String getName() {
        return name.get();
    }
	
	public Boolean getApplianceState() {
		return applianceState;
	}
	
	public void setApplianceState(Boolean applianceState) {
		this.applianceState = applianceState;
		notifyObservers();
	}
	
	public void setHomeState(HomeState homeState) {
		this.homeState = homeState;
	}
	
	public ApplianceEnum getApplianceEnum() {
		return applianceEnum;
	}
	
	public RuleEnum getRuleEnum() {
		return ruleEnum;
	}
	
	public List<AbstractAction> getActions() {
		return actions;
	}
	
	private void notifyObservers() {
		for(IObserver observer : observers) {
			observer.update();
		}
	}
	
	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}
	
	@Override
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AbstractAppliance))
			return false;
		AbstractAppliance appliance = (AbstractAppliance) o;
		return Objects.equals(getName(), appliance.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
