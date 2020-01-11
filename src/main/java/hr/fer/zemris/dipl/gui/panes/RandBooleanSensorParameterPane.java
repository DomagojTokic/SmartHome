package hr.fer.zemris.dipl.gui.panes;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.gui.decorated.DoubleValueSpinner;
import hr.fer.zemris.dipl.model.rules.conditions.BooleanCondition;
import hr.fer.zemris.dipl.model.sensors.BooleanValueSensor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Domagoj on 25.5.2017..
 */
public class RandBooleanSensorParameterPane {
	
	private BooleanValueSensor sensor;
	
	private AnchorPane anchorPane;
	
	private String panePath = "/fxml/templates/rand_boolean_parameter.fxml";
	
	private Label nameLabel;
	
	private ChoiceBox<BooleanCondition> conditionChoiceBox;
	
	private Spinner<Double> changeChanceSpinner;
	
	private String sensorNameLabelId = "sensorName";
	private String valueFieldId = "startValue";
	private String changeChanceFieldId = "changeChance";
	
	public RandBooleanSensorParameterPane(BooleanValueSensor sensor) throws IOException {
		this.sensor = sensor;
		
		URL paneURL = getClass().getResource(panePath);
		anchorPane = FXMLLoader.load(paneURL);
		
		List<Node> parameterFields = anchorPane.getChildren();
		for (Node parameterField : parameterFields) {
			String parameterFieldId = parameterField.getId();
			
			if (parameterFieldId != null) {
				if (parameterFieldId.equals(sensorNameLabelId)) {
					nameLabel = (Label) parameterField;
					nameLabel.setText(sensor.getName());
					
				} else if (parameterFieldId.equals(valueFieldId)) {
					conditionChoiceBox = (ChoiceBox<BooleanCondition>) parameterField;
					conditionChoiceBox.setItems(new ObservableListWrapper<>(Arrays.asList(BooleanCondition.values())));
					conditionChoiceBox.setConverter(new StringConverter<BooleanCondition>() {
						@Override
						public String toString(BooleanCondition object) {
							return object.toString();
						}
						
						@Override
						public BooleanCondition fromString(String string) {
							return BooleanCondition.valueOf(string);
						}
					});
					conditionChoiceBox.setValue(BooleanCondition.getBooleanCondition(sensor.getCurrentState()));
					
				} else if (parameterFieldId.equals(changeChanceFieldId)) {
					changeChanceSpinner = (Spinner<Double>) parameterField;
					DoubleValueSpinner.convertToDoubleValueSpinner(changeChanceSpinner);
					changeChanceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0,
							sensor.getChangeChance() * 100, 0.5));
				}
			}
		}
	}
	
	public BooleanValueSensor getSensor() {
		return sensor;
	}
	
	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
	
	public boolean getState() {
		return BooleanCondition.getBoolean(conditionChoiceBox.getValue());
	}
	
	public Double getChangeChance() {
		return changeChanceSpinner.getValue()/100;
	}
}
