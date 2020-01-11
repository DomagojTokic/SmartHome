package hr.fer.zemris.dipl.gui.panes;

import hr.fer.zemris.dipl.gui.decorated.DoubleValueSpinner;
import hr.fer.zemris.dipl.model.sensors.NumericValueSensor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Domagoj on 25.5.2017..
 */
public class NumericSensorParameterPane {
	
	private NumericValueSensor sensor;
	
	private AnchorPane anchorPane;
	
	private String panePath = "/fxml/templates/numeric_parameter.fxml";
	
	private Label nameLabel;
	
	private Spinner<Double> valueSpinner;
	
	private Spinner<Double> weightValueSpinner;
	
	private Spinner<Double> minChangeSpinner;
	
	private Spinner<Double> maxChangeSpinner;
	
	private String sensorNameLabelId = "sensorName";
	private String valueFieldId = "startValue";
	private String minChangeFieldId = "minChange";
	private String maxChangeFieldId = "maxChange";
	private String weightValueFieldId = "weightValue";
	
	public NumericSensorParameterPane(NumericValueSensor sensor) throws IOException {
		this.sensor = sensor;
		
		URL paneURL = getClass().getResource(panePath);
		anchorPane = FXMLLoader.load(paneURL);
		
		List<Node> parameterFields = anchorPane.getChildren();
		for (Node parameterField : parameterFields) {
			String parameterFieldId = parameterField.getId();
			
			if (parameterFieldId != null) {
				if (parameterFieldId.equals(sensorNameLabelId)) {
					nameLabel = (Label) parameterField;
					nameLabel.setText(sensor.getName() + " (" + sensor.getUnit() + ")");
					
				} else if (parameterFieldId.equals(valueFieldId)) {
					valueSpinner = (Spinner<Double>) parameterField;
					valueSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(sensor.getMinValue
							(), sensor.getMaxValue(), sensor.getCurrentValue(), sensor.getStepValue()));
					DoubleValueSpinner.convertToDoubleValueSpinner(valueSpinner);
					
				} else if (parameterFieldId.equals(weightValueFieldId)) {
					weightValueSpinner = (Spinner<Double>) parameterField;
					weightValueSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(sensor.getMinValue
							(), sensor.getMaxValue(), sensor.getWeightValue(), sensor.getStepValue()));
					DoubleValueSpinner.convertToDoubleValueSpinner(weightValueSpinner);
					
				} else if (parameterFieldId.equals(minChangeFieldId)) {
					minChangeSpinner = (Spinner<Double>) parameterField;
					minChangeSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01,
							sensor.getMaxValue(), sensor.getMinChange(), sensor.getMinChange()));
					DoubleValueSpinner.convertToDoubleValueSpinner(minChangeSpinner);
					
				} else if (parameterFieldId.equals(maxChangeFieldId)) {
					maxChangeSpinner = (Spinner<Double>) parameterField;
					maxChangeSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01,
							sensor.getMaxValue(), sensor.getMaxChange(), sensor.getMinChange()));
					DoubleValueSpinner.convertToDoubleValueSpinner(maxChangeSpinner);
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
	
	public Double getValue() {
		return valueSpinner.getValue();
	}
	
	public Double getWeightValue() {
		return weightValueSpinner.getValue();
	}
	
	public Double getMinChange() {
		return minChangeSpinner.getValue();
	}
	
	public Double getMaxChange() {
		return maxChangeSpinner.getValue();
	}
}
