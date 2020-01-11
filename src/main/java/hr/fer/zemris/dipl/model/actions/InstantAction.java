package hr.fer.zemris.dipl.model.actions;

import hr.fer.zemris.dipl.gui.visitors.InstantActionVisitor;
import hr.fer.zemris.dipl.model.interfaces.IProcess;
import hr.fer.zemris.dipl.model.rules.conditions.BooleanCondition;

/**
 * Created by Domagoj on 3.6.2017..
 */
public abstract class InstantAction extends AbstractAction implements IProcess {
	
	protected Boolean state;
	
	protected transient InstantActionVisitor visitor;
	
	public InstantAction() {
		valueType = ValueType.BOOLEAN;
	}
	
	@Override
	public void execute() throws InterruptedException {
		executeAction();
		visitor.visit();
	}
	
	public Boolean getValue() {
		return state;
	}
	
	public void setValue(Boolean state) {
		this.state = state;
	}
	
	@Override
	public String getValueString() {
		return BooleanCondition.getBooleanCondition(state).toString();
	}
	
	public void setVisitor(InstantActionVisitor visitor) {
		this.visitor = visitor;
	}
}
