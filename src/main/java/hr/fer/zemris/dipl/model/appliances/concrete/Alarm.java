package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.AlarmAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 24.4.2017..
 */
public class Alarm extends AbstractAppliance {

    public Alarm() {
        name = new SimpleStringProperty("Alarm");
        applianceEnum = ApplianceEnum.ALARM;
        ruleEnum = RuleEnum.ALARM;
        actions.add(new AlarmAction(this));
    }
    
    @Override
    public Boolean getApplianceState() {
        return homeState.getAlarm();
    }
}
