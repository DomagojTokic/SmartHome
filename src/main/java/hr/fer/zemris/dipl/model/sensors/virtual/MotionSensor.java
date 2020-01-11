package hr.fer.zemris.dipl.model.sensors.virtual;

import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.BooleanValueSensor;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Implementation Of {@link BooleanValueSensor} where value is motion presence.
 */
public class MotionSensor extends BooleanValueSensor {

    public MotionSensor(HomeState homeState) {
        name = new SimpleStringProperty("Motion sensor");
        this.homeState = homeState;
        homeState.setMotionDetected(false);
        measurement = "Motion";
        sensorEnum = SensorEnum.MOTION;
        ruleEnum = RuleEnum.MOTION;
    }
    
    
    @Override
    public double getChangeChance() {
        return homeState.getMotionChangePercentage();
    }
    
    @Override
    public void setChangeChance(double changeChance) {
        homeState.setMotionChangePercentage(changeChance);
    }
    
    @Override
    public void setState(boolean state) {
        homeState.setMotionDetected(state);
    }
    
    @Override
    public boolean getCurrentState() {
        return homeState.getMotionDetected();
    }
}
