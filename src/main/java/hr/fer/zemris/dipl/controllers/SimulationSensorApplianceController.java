package hr.fer.zemris.dipl.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.model.*;
import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.rules.AbstractRule;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.AbstractSensor;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.*;

/**
 * Created by Domagoj on 20.4.2017..
 */
public class SimulationSensorApplianceController implements ISimulationController {
	
	@FXML
	private Label simulationNameLabel;
	
	@FXML
	private ChoiceBox<SensorEnum> sensorChoiceBox;
	
	@FXML
	private TableView<SensorEnum> sensorTableView;
	
	@FXML
	private TableColumn<SensorEnum, String> sensorNameColumn;
	
	@FXML
	private TableColumn<SensorEnum, SensorEnum> sensorRemoveColumn;
	
	@FXML
	private ChoiceBox<ApplianceEnum> applianceChoiceBox;
	
	@FXML
	private TableView<ApplianceEnum> applianceTableView;
	
	@FXML
	private TableColumn<ApplianceEnum, String> applianceNameColumn;
	
	@FXML
	private TableColumn<ApplianceEnum, ApplianceEnum> applianceRemoveColumn;
	
	private Simulation simulation;
	
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void postInitialize() {
		simulationNameLabel.setText(simulation.getName() + " sensors and appliances");
		
		setSensorChoiceBox();
		setSensorTableView();
		setApplianceChoiceBox();
		setApplianceTableView();
	}
	
	private void setSensorChoiceBox() {
		ObservableList<SensorEnum> sensorList = new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(SensorEnum
				.values())));
		sensorList.removeAll(simulation.getSensorEnums());
		
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
		if (!sensorList.isEmpty()) {
			sensorChoiceBox.setValue(sensorList.get(0));
		}
	}
	
	private void setSensorTableView() {
		sensorNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().toString()));
		sensorRemoveColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		sensorRemoveColumn.setCellFactory(param -> new ButtonTableCell<SensorEnum>("X") {
			@Override
			public void event(SensorEnum sensor) {
				if (isSensorRemovalValid(sensor)) {
					simulation.getSensorEnums().remove(sensor);
					sensorTableView.refresh();
					sensorChoiceBox.getItems().add(sensor);
					if(sensorChoiceBox.getItems().size() == 1) {
						sensorChoiceBox.setValue(sensorChoiceBox.getItems().get(0));
					}
				} else {
					Utility.getUtility().showWarningDialog("Unable to remove", "Unable to remove sensor", "A rule " +
							"exists which uses reading of sensor you're trying to remove. Please remove rule before " +
							"removing the sensor.");
				}
			}
		});
		
		sensorTableView.setItems(new ObservableListWrapper<>(simulation.getSensorEnums()));
	}
	
	private boolean isSensorRemovalValid(SensorEnum sensorEnum) {
		boolean valid = true;
		
		RuleEnum sensorRuleEnum;
		Set<RuleEnum> ruleEnums = new HashSet<>();
		
		sensorRuleEnum = AbstractSensor.createSensor(sensorEnum, new HomeState()).getRuleEnum();
		
		for (RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
			HomeRule homeRule = ruleProcessPair.getRule();
			for (AbstractRule rule : homeRule.getConditions()) {
				ruleEnums.add(rule.getRuleEnum());
			}
		}
		
		if (ruleEnums.contains(sensorRuleEnum)) {
			valid = false;
		}
		
		return valid;
	}
	
	
	private void setApplianceChoiceBox() {
		ObservableList<ApplianceEnum> applianceList = new ObservableListWrapper<>(new ArrayList<>(Arrays.asList(ApplianceEnum
				.values())));
		applianceList.removeAll(simulation.getApplianceEnums());
		
		applianceChoiceBox.setConverter(new StringConverter<ApplianceEnum>() {
			@Override
			public String toString(ApplianceEnum appliance) {
				return appliance.toString();
			}
			
			@Override
			public ApplianceEnum fromString(String string) {
				return ApplianceEnum.valueOf(string);
			}
		});
		
		applianceChoiceBox.setItems(applianceList);
		if (!applianceList.isEmpty()) {
			applianceChoiceBox.setValue(applianceList.get(0));
		}
	}
	
	private void setApplianceTableView() {
		applianceTableView.setItems(new ObservableListWrapper<>(simulation.getApplianceEnums()));
		
		applianceNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().toString()));
		applianceRemoveColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		applianceRemoveColumn.setCellFactory(param -> new ButtonTableCell<ApplianceEnum>("X") {
			@Override
			public void event(ApplianceEnum applianceEnum) {
				if (isApplianceRemovalValid(applianceEnum)) {
					simulation.getApplianceEnums().remove(applianceEnum);
					applianceTableView.refresh();
					applianceChoiceBox.getItems().add(applianceEnum);
					if(applianceChoiceBox.getItems().size() == 1) {
						applianceChoiceBox.setValue(applianceChoiceBox.getItems().get(0));
					}
				} else {
					Utility.getUtility().showWarningDialog("Unable to remove", "Unable to remove appliance", "A " +
							"process exists which uses the appliance you're trying to remove. Please remove the " +
							"process before removing the appliance.");
				}
			}
		});
		
		applianceTableView.setItems(new ObservableListWrapper<>(simulation.getApplianceEnums()));
	}
	
	private boolean isApplianceRemovalValid(ApplianceEnum applianceEnum) {
		boolean valid = true;
		
		for (RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
			HomeProcess process = ruleProcessPair.getProcess();
			
			for(AbstractAction action : process.getActions()) {
				if(action.getAppliance().getApplianceEnum().equals(applianceEnum)) {
					valid = false;
				}
			}
		}
		
		return valid;
	}
	
	
	@FXML
	private void addSensor() {
		SensorEnum sensor = sensorChoiceBox.getValue();
		
		if (sensor == null) {
			Utility.getUtility().showWarningDialog("Add sensor", "No selected sensor",
					"Please select sensor before trying to add!");
			return;
		}
		sensorChoiceBox.getItems().remove(sensor);
		if (!sensorChoiceBox.getItems().isEmpty()) {
			sensorChoiceBox.setValue(sensorChoiceBox.getItems().get(0));
		}
		
		simulation.getSensorEnums().add(sensor);
		sensorTableView.refresh();
	}
	
	@FXML
	private void addAppliance() {
		ApplianceEnum applianceEnum = applianceChoiceBox.getValue();
		
		if (applianceEnum == null) {
			Utility.getUtility().showWarningDialog("Add appliance", "No selected appliance",
					"Please select appliance before trying to add!");
			return;
		}
		
		applianceChoiceBox.getItems().remove(applianceEnum);
		if (!applianceChoiceBox.getItems().isEmpty()) {
			applianceChoiceBox.setValue(applianceChoiceBox.getItems().get(0));
		}
		
		simulation.getApplianceEnums().add(applianceEnum);
		applianceTableView.refresh();
	}
	
	@FXML
	public void back() {
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_view.fxml", simulation);
	}
	
	@FXML
	public void done() {
		if (simulation.getApplianceEnums().isEmpty() || simulation.getSensorEnums().isEmpty()) {
			if (simulation.getApplianceEnums().isEmpty() && simulation.getSensorEnums().isEmpty()) {
				Utility.getUtility().showWarningDialog("Create simulation", "No sensors and appliances in simulation",
						"Please add sensors and appliances to your simulation");
				return;
			}
			
			if (simulation.getSensorEnums().isEmpty()) {
				Utility.getUtility().showWarningDialog("Create simulation", "No sensors in simulation",
						"Please add sensors to your simulation");
				return;
			}
			
			if (simulation.getApplianceEnums().isEmpty()) {
				Utility.getUtility().showWarningDialog("Create simulation", "No appliance in simulation",
						"Please add appliances to your simulation");
				return;
			}
		}
		
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_view.fxml", simulation);
	}
}
