package hr.fer.zemris.dipl.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.model.HomeProcess;
import hr.fer.zemris.dipl.model.HomeRule;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.util.StringConverter;

import java.util.List;

/**
 * Created by Domagoj on 15.6.2017..
 */
public class SimulationRuleProcessController implements ISimulationController {
	
	@FXML
	private Label simulationNameLabel;
	
	@FXML
	private ChoiceBox<HomeRule> ruleChoiceBox;
	
	@FXML
	private ChoiceBox<HomeProcess> processChoiceBox;
	
	@FXML
	private TableView<RuleProcessPair> ruleProcessPairTableView;
	
	@FXML
	private TableColumn<RuleProcessPair, String> ruleColumn;
	
	@FXML
	private TableColumn<RuleProcessPair, String> processColumn;
	
	@FXML
	private TableColumn<RuleProcessPair, RuleProcessPair> ruleProcessRemoveColumn;
	
	private Simulation simulation;
	
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public void postInitialize() {
		simulationNameLabel.setText(simulation.getName() + " rules and processes");
		
		setRuleChoiceBox();
		setProcessChoiceBox();
		setRuleProcessTableView();
	}
	
	private void setRuleChoiceBox() {
		ruleChoiceBox.setConverter(new StringConverter<HomeRule>() {
			@Override
			public String toString(HomeRule object) {
				return object.getName();
			}
			
			@Override
			public HomeRule fromString(String name) {
				List<HomeRule> ruleList = Main.getRules();
				for (HomeRule rule : ruleList) {
					if (name.equals(rule.getName())) {
						return rule;
					}
				}
				return null;
			}
		});
		ruleChoiceBox.setItems(Utility.getAvailableRules(simulation));
		if (!ruleChoiceBox.getItems().isEmpty()) {
			ruleChoiceBox.setValue(ruleChoiceBox.getItems().get(0));
		}
	}
	
	
	private void setProcessChoiceBox() {
		processChoiceBox.setConverter(new StringConverter<HomeProcess>() {
			@Override
			public String toString(HomeProcess object) {
				return object.getName();
			}
			
			@Override
			public HomeProcess fromString(String name) {
				List<HomeProcess> processList = Main.getProcesses();
				for (HomeProcess process : processList) {
					if (name.equals(process.getName())) {
						return process;
					}
				}
				return null;
			}
		});
		
		processChoiceBox.setItems(Utility.getAvailableProcesses(simulation));
		if (!processChoiceBox.getItems().isEmpty()) {
			processChoiceBox.setValue(processChoiceBox.getItems().get(0));
		}
	}
	
	private void setRuleProcessTableView() {
		ruleColumn.setCellValueFactory(new PropertyValueFactory<>("rule"));
		processColumn.setCellValueFactory(value -> value.getValue().getProcess().getObservableName());
		
		ruleProcessRemoveColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		ruleProcessRemoveColumn.setCellFactory(param -> new ButtonTableCell<RuleProcessPair>("X") {
			@Override
			public void event(RuleProcessPair ruleProcessPair) {
				simulation.getRuleProcessPairs().remove(ruleProcessPair);
				ruleProcessPairTableView.refresh();
			}
		});
		
		ruleProcessPairTableView.setItems(new ObservableListWrapper<>(simulation.getRuleProcessPairs()));
	}
	
	@FXML
	public void addRuleProcessPair() {
		HomeRule rule = ruleChoiceBox.getValue();
		HomeProcess process = processChoiceBox.getValue();
		
		if (rule == null || process == null) {
			Utility.getUtility().showWarningDialog("Invalid pair", "Invalid rule-process pair", "Please select a " +
					"valid pair of rule and process or create new.");
			return;
		}
		
		RuleProcessPair ruleProcessPair = new RuleProcessPair(rule, process);
		simulation.addRuleProcessPair(ruleProcessPair);
		ruleProcessPairTableView.refresh();
	}
	
	@FXML
	private void switchToNewRule() {
		Utility.getUtility().switchToSimulationPage("/fxml/pages/new_rule.fxml", simulation);
	}
	
	@FXML
	private void switchToNewProcess() {
		Utility.getUtility().switchToSimulationPage("/fxml/pages/new_process.fxml", simulation);
	}
	
	@FXML
	private void back() {
		if (simulation.getRuleProcessPairs().isEmpty()) {
			if (!Utility.getUtility().showConfirmationDialog("No rule process pairs added to simulation. Do you want" +
					" to continue?")) {
				return;
			}
		}
		
		Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_view.fxml", simulation);
	}
	
	@FXML
	private void done() {
		if (simulation.getRuleProcessPairs().isEmpty()) {
			if (!Utility.getUtility().showConfirmationDialog("No rule process pairs added to simulation. Do you want" +
					" to continue?")) {
				return;
			}
		}
		Utility.getUtility().switchPane("/fxml/pages/simulations.fxml");
	}
	
}
