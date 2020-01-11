package hr.fer.zemris.dipl.controllers;

import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.gui.panes.AppliancePane;
import hr.fer.zemris.dipl.gui.panes.NumericSensorPane;
import hr.fer.zemris.dipl.gui.panes.RandBooleanSensorPane;
import hr.fer.zemris.dipl.gui.observers.*;
import hr.fer.zemris.dipl.gui.visitors.InstantActionVisitor;
import hr.fer.zemris.dipl.gui.visitors.TemporalActionVisitor;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.SimulationRunner;
import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.actions.InstantAction;
import hr.fer.zemris.dipl.model.HomeProcess;
import hr.fer.zemris.dipl.model.actions.TemporalAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.sensors.AbstractSensor;
import hr.fer.zemris.dipl.model.sensors.NumericValueSensor;
import hr.fer.zemris.dipl.model.sensors.BooleanValueSensor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.easyrules.quartz.RulesEngineSchedulerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Domagoj on 22.5.2017..
 */
public class SimulationRunController implements ISimulationController {
	
	/** Simulation name label */
	@FXML
	private Label simulationRunNameLabel;
	
	/** Vertical box for displaying sensor parameter panes */
	@FXML
	private VBox sensorVBox;
	
	/** Vertical box for displaying appliance homeState panes */
	@FXML
	private VBox applianceVBox;
	
	@FXML
	private TextArea processTextArea;
	
	@FXML
	private Button setParametersButton;
	
	/** Simulation shown on screen */
	private Simulation simulation;
	
	private Set<AbstractAppliance> applianceSet;
	
	/** Every numeric sensor has it's pane for setting parameters. */
	private List<NumericSensorPane> numericSensorPanes = new ArrayList<>();
	
	/** Every random boolean sensor has it's pane for setting parameters. */
	private List<RandBooleanSensorPane> randBooleanParameterPanes = new ArrayList<>();
	
	private SimulationRunner simulationRunner;
	
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void postInitialize() throws IOException {
		simulationRunner = Main.getSimulationRunner();
		
		simulationRunNameLabel.setText("Run " + simulation.getName());
		setSensorPane();
		setAppliancePane();
		setSimulationInfoTextArea();
	}
	
	private void setSensorPane() throws IOException {
		for (AbstractSensor sensor : simulationRunner.getSensors()) {
			if (sensor instanceof NumericValueSensor) {
				NumericValueSensor numericValueSensor = (NumericValueSensor) sensor;
				NumericSensorPane pane = new NumericSensorPane(numericValueSensor);
				numericSensorPanes.add(pane);
				numericValueSensor.addObserver(new NumericValueSensorObserver(pane));
				sensorVBox.getChildren().add(pane.getAnchorPane());
				
			} else if (sensor instanceof BooleanValueSensor) {
				BooleanValueSensor booleanValueSensor = (BooleanValueSensor) sensor;
				RandBooleanSensorPane pane = new RandBooleanSensorPane(booleanValueSensor);
				randBooleanParameterPanes.add(pane);
				booleanValueSensor.addObserver(new RandomBooleanValueSensorObserver(pane));
				sensorVBox.getChildren().add(pane.getAnchorPane());
			}
		}
	}
	
	private void setAppliancePane() throws IOException {
		applianceSet = new HashSet<>();
		
		for(RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
			HomeProcess process = ruleProcessPair.getProcess();
			for(AbstractAction action : process.getActions()) {
				applianceSet.add(action.getAppliance());
			}
		}
		
		Utility.setAppliances(simulation, applianceSet);
		
		List<ApplianceEnum> activeApplianceEnums = new ArrayList<>();
		for(AbstractAppliance appliance : applianceSet) {
			AppliancePane appliancePane = new AppliancePane(appliance);
			ApplianceObserver applianceObserver = new ApplianceObserver(appliancePane);
			appliance.addObserver(applianceObserver);
			applianceVBox.getChildren().add(appliancePane.getAnchorPane());
			activeApplianceEnums.add(appliance.getApplianceEnum());
		}
		
		List<ApplianceEnum> inactiveApplianceEnums = new ArrayList<>(simulation.getApplianceEnums());
		inactiveApplianceEnums.removeAll(activeApplianceEnums);
		List<AbstractAppliance> inactiveAppliances = Utility.getAvailableAppliances(inactiveApplianceEnums);
		
		for(AbstractAppliance appliance : inactiveAppliances) {
			AppliancePane appliancePane = new AppliancePane(appliance);
			applianceVBox.getChildren().add(appliancePane.getAnchorPane());
		}
	}
	
	private void setSimulationInfoTextArea() {
		for(RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
			HomeProcess process = ruleProcessPair.getProcess();
			process.addObserver(new HomeProcessObserver(ruleProcessPair.getProcess(), processTextArea));
			
			for(AbstractAction action : process.getActions()) {
				if(action instanceof TemporalAction) {
					TemporalAction temporalAction = (TemporalAction) action;
					temporalAction.setVisitor(new TemporalActionVisitor(temporalAction, processTextArea));
				} else if(action instanceof InstantAction) {
					InstantAction instantAction = (InstantAction) action;
					instantAction.setVisitor(new InstantActionVisitor(instantAction, processTextArea));
				}
			}
		}
	}
	
	@FXML
	private void startSimulation() throws InterruptedException {
		disableNewValueFields();
		setParametersButton.setDisable(true);
		simulationRunner.run();
		processTextArea.appendText("Started simulation...\n");
	}
	
	private void disableNewValueFields() {
		for (NumericSensorPane pane : numericSensorPanes) {
			pane.disableNewValueField();
		}
		for (RandBooleanSensorPane pane : randBooleanParameterPanes) {
			pane.disableNewValueField();
		}
	}
	
	@FXML
	private void pauseSimulation() throws InterruptedException, RulesEngineSchedulerException {
		simulationRunner.pause();
		enableNewValueFields();
		setParametersButton.setDisable(false);
		processTextArea.appendText("Simulation paused...\n");
	}
	
	private void enableNewValueFields() {
		for (NumericSensorPane pane : numericSensorPanes) {
			pane.enableNewValueField();
		}
		for (RandBooleanSensorPane pane : randBooleanParameterPanes) {
			pane.enableNewValueField();
		}
	}
	
	@FXML
	private void setParameters() {
		for (NumericSensorPane pane : numericSensorPanes) {
			double newValue =  pane.getNewValue();
			pane.getSensor().setCurrentValue(newValue);
			pane.setValueTextField(newValue);
		}
		for (RandBooleanSensorPane pane : randBooleanParameterPanes) {
			boolean newState =  pane.getNewState();
			pane.getSensor().setState(newState);
			pane.setStateTextField(newState);
		}
	}
	
	@FXML
	private void end() throws RulesEngineSchedulerException {
		simulationRunner.pause();
		Main.setSimulationRunner(null);
		for(AbstractAppliance appliance : applianceSet) {
			appliance.setApplianceState(false);
		}
		Utility.getUtility().switchPane("/fxml/pages/simulations.fxml");
	}
	
	@FXML
	private void back() {
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_parameters.fxml", simulation);
	}
}
