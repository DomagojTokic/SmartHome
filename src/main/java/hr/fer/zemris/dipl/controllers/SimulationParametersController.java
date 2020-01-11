package hr.fer.zemris.dipl.controllers;

import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.gui.panes.NumericSensorParameterPane;
import hr.fer.zemris.dipl.gui.panes.RandBooleanSensorParameterPane;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.SimulationRunner;
import hr.fer.zemris.dipl.model.sensors.AbstractSensor;
import hr.fer.zemris.dipl.model.sensors.NumericValueSensor;
import hr.fer.zemris.dipl.model.sensors.BooleanValueSensor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Domagoj on 22.5.2017..
 */
public class SimulationParametersController implements ISimulationController {
	
	/** Simulation name label */
	@FXML
	private Label simulationParametersNameLabel;
	
	/** Vertical box for displaying sensor parameter panes */
	@FXML
	private VBox parametersVBox;
	
	/** Simulation shown on screen */
	private Simulation simulation;
	
	/**
	 * Every numeric sensor has it's pane for setting starting parameters.
	 */
	private List<NumericSensorParameterPane> numericSensorParameterPanes = new ArrayList<>();
	
	/**
	 * Every random boolean sensor has it's pane for setting starting parameters.
	 */
	private List<RandBooleanSensorParameterPane> randBooleanParameterPanes = new ArrayList<>();
	
	private SimulationRunner simulationRunner;
	
	public void setSimulation(Simulation simulation) {
		try {
			this.simulation = (Simulation) simulation.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public void postInitialize() throws IOException {
		simulationRunner = Main.getSimulationRunner();
		if(Main.getSimulationRunner() == null || !Main.getSimulationRunner().getSimulation().equals(simulation)) {
			simulationRunner = new SimulationRunner(simulation);
			Main.setSimulationRunner(simulationRunner);
		}
		
		simulationParametersNameLabel.setText(simulation.getName() + " parameters");
		
		for (AbstractSensor sensor : simulationRunner.getSensors()) {
			if (sensor instanceof NumericValueSensor) {
				NumericSensorParameterPane pane = new NumericSensorParameterPane((NumericValueSensor) sensor);
				numericSensorParameterPanes.add(pane);
				parametersVBox.getChildren().add(pane.getAnchorPane());
				
			} else if (sensor instanceof BooleanValueSensor) {
				RandBooleanSensorParameterPane pane = new RandBooleanSensorParameterPane(
						(BooleanValueSensor) sensor);
				randBooleanParameterPanes.add(pane);
				parametersVBox.getChildren().add(pane.getAnchorPane());
			}
		}
	}
	
	@FXML
	private void goToRunSimulation() {
		for(NumericSensorParameterPane numericSensorParameterPane : numericSensorParameterPanes) {
			NumericValueSensor sensor = numericSensorParameterPane.getSensor();
			sensor.setCurrentValue(numericSensorParameterPane.getValue());
			sensor.setWeightValue(numericSensorParameterPane.getWeightValue());
			if(numericSensorParameterPane.getMinChange() > numericSensorParameterPane.getMaxChange()) {
				Utility.getUtility().showWarningDialog("Invalid input", "Invalid min and max change", "Minimum " +
						"change can't be greater than maximum change!");
				return;
			}
			sensor.setMinChange(numericSensorParameterPane.getMinChange());
			sensor.setMaxChange(numericSensorParameterPane.getMaxChange());
		}
		
		for(RandBooleanSensorParameterPane randBooleanSensorParameterPane : randBooleanParameterPanes) {
			BooleanValueSensor sensor = randBooleanSensorParameterPane.getSensor();
			sensor.setState(randBooleanSensorParameterPane.getState());
			sensor.setChangeChance(randBooleanSensorParameterPane.getChangeChance());
		}
		
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_run.fxml", simulation);
	}
	
	@FXML
	private void back() {
		Utility.getUtility().switchPane("/fxml/pages/simulations.fxml");
	}
}
