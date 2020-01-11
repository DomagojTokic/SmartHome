package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.HeatingAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 24.4.2017..
 */
public class CentralHeating extends AbstractAppliance {

    public CentralHeating() {
        name = new SimpleStringProperty("Central heating");
        applianceEnum = ApplianceEnum.CENTRAL_HEATING;
        actions.add(new HeatingAction(1.5, 0.05, this));
    }
    
}
