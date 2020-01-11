package hr.fer.zemris.dipl.gui.observers;

import hr.fer.zemris.dipl.gui.panes.AppliancePane;
import javafx.application.Platform;

/**
 * Created by Domagoj on 11.6.2017..
 */
public class ApplianceObserver implements IObserver{
	
	private AppliancePane appliancePane;
	
	public ApplianceObserver(AppliancePane appliancePane) {
		this.appliancePane = appliancePane;
	}
	
	@Override
	public void update() {
		Platform.runLater(() -> appliancePane.setStateTextField(appliancePane.getAppliance().getApplianceState()));
	}
}
