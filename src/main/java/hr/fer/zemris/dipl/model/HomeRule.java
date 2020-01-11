package hr.fer.zemris.dipl.model;

import hr.fer.zemris.dipl.model.rules.AbstractRule;
import org.easyrules.core.CompositeRule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A rule which consists of multiple conditions all of which check on attribute of {@link HomeState}. If all
 * conditions are satisfied during a firing of rule, a {@link HomeProcess} will be executed.
 */
public class HomeRule extends CompositeRule implements Cloneable, Serializable {
    
    /** List of rule conditions */
    private List<AbstractRule> conditions;
    
    /** Process which starts when rule is satisfied */
    private transient HomeProcess process;
    
    /**
     * Creates a new instance of HomeRule
     * @param name Rule name
     * @param description Rule description
     */
    public HomeRule(String name, String description) {
        super(name, description);
        conditions = new ArrayList<>();
    }
    
    @Override
    public void execute() throws Exception {
        process.execute();
    }
    
    @Override
    public void addRule(Object rule) {
        conditions.add((AbstractRule) rule);
        super.addRule(rule);
    }
    
    @Override
    public void removeRule(Object rule) {
        conditions.remove(rule);
        super.removeRule(rule);
    }
    
    public void setHomeState(HomeState homeState) {
        for(AbstractRule rule : conditions) {
            rule.setHomeState(homeState);
        }
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setProcess(HomeProcess process) {
        this.process = process;
    }
    
    public List<AbstractRule> getConditions() {
        return conditions;
    }
    
    public void setConditions(List<AbstractRule> conditions) {
        this.conditions = conditions;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
	    HomeRule ruleClone = new HomeRule(this.getName(), this.getDescription());
	    
	    ruleClone.conditions = new ArrayList<>();
	    for(AbstractRule condition : this.getConditions()) {
		    ruleClone.addRule((AbstractRule) condition.clone());
	    }
	
	    return ruleClone;
    }
}
