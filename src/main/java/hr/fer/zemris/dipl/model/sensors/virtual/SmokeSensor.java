package hr.fer.zemris.dipl.model.sensors.virtual;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.BooleanValueSensor;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Implementation Of {@link BooleanValueSensor} where value is presence of smoke.
 */
public class SmokeSensor extends BooleanValueSensor {

    public SmokeSensor(HomeState homeState) {
        name = new SimpleStringProperty("Smoke sensor");
        this.homeState = homeState;
	    measurement = "Smoke";
        sensorEnum = SensorEnum.SMOKE;
        ruleEnum = RuleEnum.SMOKE;
    }
    
    @Override
    public double getChangeChance() {
        return homeState.getSmokeChangePercentage();
    }
    
    @Override
    public void setChangeChance(double changeChance) {
        homeState.setSmokeChangePercentage(changeChance);
    }
    
    @Override
    public void setState(boolean state) {
        homeState.setSmokeDetected(state);
    }
    
    @Override
    public boolean getCurrentState() {
        return homeState.getSmokeDetected();
    }
}
