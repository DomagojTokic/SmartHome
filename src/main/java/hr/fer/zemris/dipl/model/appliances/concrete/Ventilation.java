package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.CarbonDioxideAction;
import hr.fer.zemris.dipl.model.actions.concrete.HumidityAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 24.4.2017..
 */
public class Ventilation extends AbstractAppliance {

    public Ventilation() {
        name = new SimpleStringProperty("Ventilation");
        applianceEnum = ApplianceEnum.VENTILATION;
        actions.add(new CarbonDioxideAction(2.0,0.1, this));
        actions.add(new HumidityAction(2.0, 0.1, this));
    }
    
}
