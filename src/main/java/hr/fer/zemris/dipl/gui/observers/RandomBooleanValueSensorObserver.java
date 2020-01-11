package hr.fer.zemris.dipl.gui.observers;

import hr.fer.zemris.dipl.gui.panes.RandBooleanSensorPane;
import javafx.application.Platform;

/**
 * Created by Domagoj on 2.6.2017..
 */
public class RandomBooleanValueSensorObserver implements IObserver {
	
	private RandBooleanSensorPane sensorPane;
	
	public RandomBooleanValueSensorObserver(RandBooleanSensorPane sensorPane) {
		this.sensorPane = sensorPane;
	}
	
	@Override
	public void update() {
		Platform.runLater(() -> {
			sensorPane.setStateTextField(sensorPane.getSensor().getCurrentState());
			sensorPane.setNewStateChoiceBox(sensorPane.getSensor().getCurrentState());
		});
	}
}
