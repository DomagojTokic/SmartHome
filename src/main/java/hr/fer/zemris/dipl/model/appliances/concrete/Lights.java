package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.LightAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 24.4.2017..
 */
public class Lights extends AbstractAppliance {

    public Lights() {
        name = new SimpleStringProperty("Lights controller");
        applianceEnum = ApplianceEnum.LIGHT;
        ruleEnum = RuleEnum.LIGHT;
        actions.add(new LightAction(this));
    }
    
    @Override
    public Boolean getApplianceState() {
        return homeState.getLight();
    }
}
