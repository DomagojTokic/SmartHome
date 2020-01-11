package hr.fer.zemris.dipl.gui.panes;

import hr.fer.zemris.dipl.gui.decorated.DoubleValueSpinner;
import hr.fer.zemris.dipl.model.sensors.NumericValueSensor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Domagoj on 26.5.2017..
 */
public class NumericSensorPane {
	
	private NumericValueSensor sensor;
	
	private AnchorPane anchorPane;
	
	private String panePath = "/fxml/templates/numeric_sensor.fxml";
	
	private Label measurementLabel;
	
	private TextField valueTextField;
	
	private Spinner<Double> newValueSpinner;
	
	private Label unitLabel;
	
	private final String measurementNameLabelId = "measurementName";
	private final String currentValueFieldId = "currentValue";
	private final String newValueFieldId = "newValue";
	private final String unitLabelId = "unit";
	
	private DecimalFormat formatter = new DecimalFormat("#0.000");
	
	public NumericSensorPane(NumericValueSensor sensor) throws IOException {
		this.sensor = sensor;

		URL paneURL = getClass().getResource(panePath);
		anchorPane = FXMLLoader.load(paneURL);
		
		List<Node> parameterFields = anchorPane.getChildren();
		for (Node parameterField : parameterFields) {
			String parameterFieldId = parameterField.getId();
			
			if (parameterFieldId != null) {
				if (parameterFieldId.equals(measurementNameLabelId)) {
					measurementLabel = (Label) parameterField;
					measurementLabel.setText(sensor.getMeasurement());
					
				} else if (parameterFieldId.equals(currentValueFieldId)) {
					valueTextField = (TextField) parameterField;
					valueTextField.setText(Double.toString(sensor.getCurrentValue()));
					
				} else if (parameterFieldId.equals(newValueFieldId)) {
					newValueSpinner = (Spinner<Double>) parameterField;
					newValueSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(sensor.getMinValue
							(), sensor.getMaxValue(), sensor.getCurrentValue(), sensor.getStepValue()));
					DoubleValueSpinner.convertToDoubleValueSpinner(newValueSpinner);
				} else if (parameterFieldId.equals(unitLabelId)) {
					unitLabel = (Label) parameterField;
					unitLabel.setText(sensor.getUnit());
					
				}
			}
		}
	}
	
	public NumericValueSensor getSensor() {
		return sensor;
	}
	
	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
	
	public void setValueTextField(double value) {
		valueTextField.setText(formatter.format(value).replace(",", "."));
	}
	
	public void setNewValueSpinner(double value) {
		newValueSpinner.getEditor().setText(formatter.format(value).replace(",", "."));
	}
	
	public double getNewValue() {
		newValueSpinner.increment(0);
		return newValueSpinner.getValue();
	}
	
	public void disableNewValueField() {
		newValueSpinner.setDisable(true);
	}
	
	public void enableNewValueField() {
		newValueSpinner.setDisable(false);
	}
}
