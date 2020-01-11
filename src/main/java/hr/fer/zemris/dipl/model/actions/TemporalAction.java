package hr.fer.zemris.dipl.model.actions;

import hr.fer.zemris.dipl.gui.visitors.TemporalActionVisitor;
import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.interfaces.IProcess;

import java.util.concurrent.Semaphore;

/**
 * Created by Domagoj on 3.6.2017..
 */
public abstract class TemporalAction extends AbstractAction implements IProcess {
	
	protected Double weight;
	protected Double multiplier;
	protected Double multiplierInc;
	protected transient TemporalActionVisitor visitor;
	
	public TemporalAction() {
		valueType = valueType.DOUBLE;
		homeState = new HomeState();
	}
	
	public abstract Double getMinValue();
	public abstract Double getMaxValue();
	public abstract Double getStepValue();
	protected abstract Semaphore getStartSemaphore();
	protected abstract Semaphore getEndSemaphore();
	
	@Override
	public void execute() throws InterruptedException {
		getStartSemaphore().acquire();
		visitor.visitStart();
		executeAction();
		getEndSemaphore().acquire();
		visitor.visitEnd();
	}
	
	@Override
	public String getValueString() {
		return weight.toString();
	}
	
	public Double getValue() {
		return weight;
	}
	
	public void setValue(Double value) {
		this.weight = value;
	}
	
	public void setVisitor(TemporalActionVisitor visitor) {
		this.visitor = visitor;
	}
}
