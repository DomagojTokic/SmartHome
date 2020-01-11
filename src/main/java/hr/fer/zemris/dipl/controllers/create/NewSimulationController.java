package hr.fer.zemris.dipl.controllers.create;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.Factory;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.*;

/**
 * Created by Domagoj on 20.4.2017..
 */
public class NewSimulationController implements Initializable {
	
	@FXML
	private TextField simulationNameField;
	
	@FXML
	private Label warningMessage;
	
	@FXML
	private ChoiceBox<SensorEnum> sensorChoiceBox;
	
	@FXML
	private ChoiceBox<ApplianceEnum> applianceChoiceBox;
	
	@FXML
	private TableView<SensorEnum> sensorTableView;
	
	@FXML
	private TableColumn<SensorEnum, String> sensorColumn;
	
	@FXML
	private TableColumn<SensorEnum, SensorEnum> sensorRemoveColumn;
	
	@FXML
	private TableView<ApplianceEnum> applianceTableView;
	
	@FXML
	private TableColumn<ApplianceEnum, String> applianceColumn;
	
	@FXML
	private TableColumn<ApplianceEnum, ApplianceEnum> applianceRemoveColumn;
	
	private ObservableList<SensorEnum> sensorsToAdd = new ObservableListWrapper<>(new ArrayList<>());
	
	private ObservableList<ApplianceEnum> appliancesToAdd = new ObservableListWrapper<>(new ArrayList<>());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeSensors();
		initializeAppliances();
	}
	
	private void initializeSensors() {
		ObservableList<SensorEnum> sensorList = new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(SensorEnum
				.values())));
		
		sensorChoiceBox.setConverter(new StringConverter<SensorEnum>() {
			@Override
			public String toString(SensorEnum sensor) {
				return sensor.toString();
			}
			
			@Override
			public SensorEnum fromString(String string) {
				return SensorEnum.valueOf(string);
			}
		});
		
		sensorChoiceBox.setItems(sensorList);
		sensorChoiceBox.setValue(sensorChoiceBox.getItems().get(0));
		
		sensorRemoveColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		sensorRemoveColumn.setCellFactory(param -> new ButtonTableCell<SensorEnum>("X") {
			@Override
			public void event(SensorEnum sensor) {
				sensorsToAdd.remove(sensor);
				sensorChoiceBox.getItems().add(sensor);
			}
		});
		
		sensorColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().toString()));
		sensorTableView.setItems(sensorsToAdd);
	}
	
	private void initializeAppliances() {
		ObservableList<ApplianceEnum> applianceList = new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(ApplianceEnum
				.values())));
		
		applianceChoiceBox.setConverter(new StringConverter<ApplianceEnum>() {
			@Override
			public String toString(ApplianceEnum applianceEnum) {
				return applianceEnum.toString();
			}
			
			@Override
			public ApplianceEnum fromString(String string) {
				return ApplianceEnum.valueOf(string);
			}
		});
		applianceChoiceBox.setItems(applianceList);
		applianceChoiceBox.setValue(applianceChoiceBox.getItems().get(0));
		
		applianceRemoveColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		applianceRemoveColumn.setCellFactory(param -> new ButtonTableCell<ApplianceEnum>("X") {
			@Override
			public void event(ApplianceEnum applianceEnum) {
				appliancesToAdd.remove(applianceEnum);
				applianceChoiceBox.getItems().add(applianceEnum);
			}
		});
		
		applianceColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().toString()));
		applianceTableView.setItems(appliancesToAdd);
	}
	
	@FXML
	private void addSensor() {
		SensorEnum sensor = sensorChoiceBox.getValue();
		
		if (sensor == null) {
			Utility.getUtility().showWarningDialog("Add sensor", "No selected sensor",
					"Please select sensor before trying to add!");
			return;
		}
		sensorsToAdd.add(sensor);
		sensorChoiceBox.getItems().remove(sensor);
		if(!sensorChoiceBox.getItems().isEmpty()) {
			sensorChoiceBox.setValue(sensorChoiceBox.getItems().get(0));
		}
	}
	
	@FXML
	private void addAppliance() {
		ApplianceEnum applianceEnum = applianceChoiceBox.getValue();
		
		if (applianceEnum == null) {
			Utility.getUtility().showWarningDialog("Add appliance", "No selected appliance",
					"Please select appliance before trying to add!");
			return;
		}
		appliancesToAdd.add(applianceEnum);
		applianceChoiceBox.getItems().remove(applianceEnum);
		if(!applianceChoiceBox.getItems().isEmpty()) {
			applianceChoiceBox.setValue(applianceChoiceBox.getItems().get(0));
		}
	}
	
	@FXML
	private void next() {
		String input = simulationNameField.getText();
		if (input.length() == 0) {
			warningMessage.setText("Please set simulation name...");
			return;
		}
		
		List<Simulation> simulations = Main.getSimulations();
		for (Simulation simulation : simulations) {
			if (input.equals(simulation.getName())) {
				warningMessage.setText("Simulation with chosen name already exists...");
				return;
			}
		}
		
		if(sensorsToAdd.isEmpty() || appliancesToAdd.isEmpty()) {
			if(sensorsToAdd.isEmpty() && appliancesToAdd.isEmpty()) {
				Utility.getUtility().showWarningDialog("Create simulation", "No sensors and appliances in simulation",
						"Please add sensors and appliances to your simulation");
				return;
			}
			
			if (sensorsToAdd.isEmpty()) {
				Utility.getUtility().showWarningDialog("Create simulation", "No sensors in simulation",
						"Please add sensors to your simulation");
				return;
			}
			
			if (appliancesToAdd.isEmpty()) {
				Utility.getUtility().showWarningDialog("Create simulation", "No appliance in simulation",
						"Please add appliances to your simulation");
				return;
			}
		}
		
		Simulation simulation = Factory.getFactory().createSimulation(input, sensorsToAdd, appliancesToAdd);
		Main.getSimulations().add(simulation);
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_rule_process.fxml", simulation);
	}
	
	@FXML
	private void back() {
		Utility.getUtility().switchPane("/fxml/pages/simulations.fxml");
	}
}
