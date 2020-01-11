package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.ShadesAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 24.4.2017..
 */
public class Shades extends AbstractAppliance {

    public Shades() {
        name = new SimpleStringProperty("Shades controller");
        applianceEnum = applianceEnum.SHADES;
        actions.add(new ShadesAction(this));
    }
    
    @Override
    public Boolean getApplianceState() {
        return homeState.getShades();
    }
}
