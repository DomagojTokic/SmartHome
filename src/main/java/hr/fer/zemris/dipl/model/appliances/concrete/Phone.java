package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.PhoneAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 24.4.2017..
 */
public class Phone extends AbstractAppliance {

    public Phone() {
        name = new SimpleStringProperty("Phone controller");
        applianceEnum = ApplianceEnum.PHONE;
        actions.add(new PhoneAction(this));
    }
    
    @Override
    public Boolean getApplianceState() {
        return homeState.getPhone();
    }
}
