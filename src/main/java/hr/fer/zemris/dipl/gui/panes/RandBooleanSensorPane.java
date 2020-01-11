package hr.fer.zemris.dipl.gui.panes;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.model.rules.conditions.BooleanCondition;
import hr.fer.zemris.dipl.model.sensors.BooleanValueSensor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Domagoj on 26.5.2017..
 */
public class RandBooleanSensorPane {
	
	private BooleanValueSensor sensor;
	
	private AnchorPane anchorPane;
	
	private String panePath = "/fxml/templates/rand_boolean_sensor.fxml";
	
	private Label measurementLabel;
	
	private TextField stateTextField;
	
	private ChoiceBox<BooleanCondition> newStateChoiceBox;
	
	private String measurementNameLabelId = "measurementName";
	private String stateFieldId = "homeState";
	private String newStateFieldId = "newState";
	
	public RandBooleanSensorPane(BooleanValueSensor sensor) throws IOException {
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
					
				} else if (parameterFieldId.equals(stateFieldId)) {
					stateTextField = (TextField) parameterField;
					stateTextField.setText(BooleanCondition.getBooleanCondition(sensor.getCurrentState()).toString());
					
				} else if (parameterFieldId.equals(newStateFieldId)) {
					newStateChoiceBox = (ChoiceBox<BooleanCondition>) parameterField;
					newStateChoiceBox.setItems(new ObservableListWrapper<>(Arrays.asList(BooleanCondition.values())));
					newStateChoiceBox.setConverter(new StringConverter<BooleanCondition>() {
						@Override
						public String toString(BooleanCondition object) {
							return object.toString();
						}
						
						@Override
						public BooleanCondition fromString(String string) {
							return BooleanCondition.valueOf(string);
						}
					});
					newStateChoiceBox.setValue(BooleanCondition.getBooleanCondition(sensor.getCurrentState()));
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
	
	public BooleanCondition getState() {
		return BooleanCondition.valueOf(stateTextField.getText());
	}
	
	public void setStateTextField(boolean state) {
		stateTextField.setText(BooleanCondition.getBooleanCondition(state).toString());
	}
	
	public void setNewStateChoiceBox(boolean state) {
		newStateChoiceBox.setValue(BooleanCondition.getBooleanCondition(state));
	}
	
	public boolean getNewState() {
		return BooleanCondition.getBoolean(newStateChoiceBox.getValue());
	}
	
	public void disableNewValueField() {
		newStateChoiceBox.setDisable(true);
	}
	
	public void enableNewValueField() {
		newStateChoiceBox.setDisable(false);
	}
}
