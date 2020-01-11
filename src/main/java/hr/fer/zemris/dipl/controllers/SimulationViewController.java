package hr.fer.zemris.dipl.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Domagoj on 26.4.2017..
 */
public class SimulationViewController implements ISimulationController {
	
	private Simulation simulation;
	
	@FXML
	private Label simulationName;
	
	@FXML
	private TableView<SensorEnum> sensorTableView;
	
	@FXML
	private TableView<ApplianceEnum> applianceTableView;
	
	@FXML
	private TableColumn<SensorEnum, String> sensorNameColumn;
	
	@FXML
	private TableColumn<ApplianceEnum, String> applianceNameColumn;
	
	@FXML
	private TableView<RuleProcessPair> ruleProcessPairTableView;
	
	@FXML
	private TableColumn<RuleProcessPair, String> ruleColumn;
	
	@FXML
	private TableColumn<RuleProcessPair, String> processColumn;
	
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void postInitialize() {
		simulationName.setText(simulation.getName());
		
		sensorNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().toString()));
		sensorTableView.setItems(new ObservableListWrapper<>(simulation.getSensorEnums()));
		
		applianceNameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().toString()));
		applianceTableView.setItems(new ObservableListWrapper<>(simulation.getApplianceEnums()));
		
		ruleColumn.setCellValueFactory(new PropertyValueFactory<>("rule"));
		processColumn.setCellValueFactory(value -> value.getValue().getProcess().getObservableName());
		ruleProcessPairTableView.setItems(new ObservableListWrapper<>(simulation.getRuleProcessPairs()));
	}
	
	@FXML
	public void switchToSimulationSensorAppliance() {
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_sensor_appliance.fxml", simulation);
	}
	
	@FXML
	public void switchToSimulationRuleProcess() {
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_rule_process.fxml", simulation);
	}
	
	@FXML
	private void back() {
		Utility.getUtility().switchPane("/fxml/pages/simulations.fxml");
	}
}
