package hr.fer.zemris.dipl.gui.observers;

import hr.fer.zemris.dipl.gui.panes.NumericSensorPane;
import javafx.application.Platform;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Domagoj on 2.6.2017..
 */
public class NumericValueSensorObserver implements IObserver{
	
	private NumericSensorPane sensorPane;
	
	private final NumberFormat formatter = new DecimalFormat("#0.000");
	
	public NumericValueSensorObserver(NumericSensorPane sensorPane) {
		this.sensorPane = sensorPane;
	}
	
	@Override
	public void update() {
		Platform.runLater(() -> {
			sensorPane.setValueTextField(sensorPane.getSensor().getCurrentValue());
			sensorPane.setNewValueSpinner(sensorPane.getSensor().getCurrentValue());
		});
	}
}
