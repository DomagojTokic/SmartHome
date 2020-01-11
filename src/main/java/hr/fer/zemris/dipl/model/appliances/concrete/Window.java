package hr.fer.zemris.dipl.model.appliances.concrete;

import hr.fer.zemris.dipl.model.actions.concrete.CarbonDioxideAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Domagoj on 24.4.2017..
 */
public class Window extends AbstractAppliance {

    public Window() {
        name = new SimpleStringProperty("Window controller");
        applianceEnum = ApplianceEnum.WINDOW;
        actions.add(new CarbonDioxideAction(1.5, 0.05, this));
    }
}
