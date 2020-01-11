package hr.fer.zemris.dipl.model;

import hr.fer.zemris.dipl.gui.observers.IObserver;
import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.interfaces.IProcess;
import hr.fer.zemris.dipl.model.interfaces.Subject;
import hr.fer.zemris.dipl.model.rules.AbstractRule;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by Domagoj on 25.4.2017..
 */
public class HomeProcess implements IProcess, Subject, Cloneable, Serializable {

    private StringProperty name;
    private StringProperty description;
    
    private transient boolean running;
	
	private List<AbstractAction> actions;
	
	private transient List<IObserver> observers = new ArrayList<>();
	
	public HomeProcess() {
		observers = new ArrayList<>();
	}

    public HomeProcess(String name, String description, List<AbstractAction> actions) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.actions = actions;
    }

    @Override
    public void execute() throws InterruptedException {
    	if(!running) {
    		running = true;
		    notifyObservers();
		    for(AbstractAction action : actions) {
		    	action.getAppliance().setApplianceState(true);
			    
			    action.execute();
			
			    action.getAppliance().setApplianceState(false);
		    }
		    running = false;
	    }
    }
    
    public StringProperty getObservableName() {
        return name;
    }

    public String getName() {
        return name.get();
    }
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getDescription() {
		return description.get();
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public List<AbstractAction> getActions() {
		return actions;
	}
	
	public void setActions(List<AbstractAction> actions) {
		this.actions = actions;
	}
	
	public void setHomeState(HomeState homeState) {
    	for(AbstractAction action : actions) {
    		action.setHomeState(homeState);
    		action.getAppliance().setHomeState(homeState);
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
	
	private void notifyObservers() {
    	for(IObserver observer : observers) {
    		observer.update();
	    }
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		HomeProcess clone = (HomeProcess) super.clone();
		clone.actions = new ArrayList<>();

		for(AbstractAction action : getActions()) {
			clone.getActions().add((AbstractAction) action.clone());
		}
		return clone;
	}
	
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		observers = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof HomeProcess))
			return false;
		HomeProcess process = (HomeProcess) o;
		return Objects.equals(getName(), process.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
