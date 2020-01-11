package hr.fer.zemris.dipl.general;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.Factory;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.controllers.ISimulationController;
import hr.fer.zemris.dipl.controllers.edit.ProcessEditController;
import hr.fer.zemris.dipl.controllers.edit.RuleEditController;
import hr.fer.zemris.dipl.model.*;
import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.rules.AbstractRule;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.AbstractSensor;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.easyrules.quartz.RulesEngineSchedulerException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Domagoj on 22.4.2017..
 */
public class Utility {
	
	private static Utility utility;
	
	private String editProcessPagePath = "/fxml/pages/edit_process.fxml";
	private String editRulePagePath = "/fxml/pages/edit_rule.fxml";
	
	private Utility() {
	}
	
	public static Utility getUtility() {
		if (utility == null) {
			utility = new Utility();
		}
		return utility;
	}
	
	public static ObservableList<HomeRule> getAvailableRules(Simulation simulation) {
		List<HomeRule> availableRules = new ArrayList<>();
		List<RuleEnum> ruleEnums = new ArrayList<>();
		List<RuleEnum> testRuleEnums;
		
		List<RuleEnum> simulationRuleEnums = getRuleEnums(simulation);
		
		for (HomeRule homeRule : Main.getRules()) {
			ruleEnums.clear();
			for (AbstractRule rule : homeRule.getConditions()) {
				ruleEnums.add(rule.getRuleEnum());
			}
			
			testRuleEnums = ruleEnums.stream().filter(simulationRuleEnums::contains).collect(Collectors.toList());
			if (testRuleEnums.size() == ruleEnums.size()) {
				availableRules.add(homeRule);
			}
		}
		
		return new ObservableListWrapper<>(availableRules);
	}
	
	
	public static ObservableList<AbstractRule> getAvailableConditions(Simulation simulation) {
		return getConditions(getRuleEnums(simulation));
	}
	
	public static ObservableList<AbstractRule> getAvailableConditions(List<Simulation> simulations) {
		List<RuleEnum> simulationRuleEnums = new ArrayList<>(getRuleEnums(simulations.get(0)));
		
		for (Simulation simulation : simulations) {
			simulationRuleEnums.retainAll(getRuleEnums(simulation));
		}
		if (!simulations.isEmpty()) {
			return getConditions(new ArrayList<>(simulationRuleEnums));
		} else {
			return getConditions(Arrays.asList(RuleEnum.values()));
		}
	}
	
	private static ObservableList<AbstractRule> getConditions(List<RuleEnum> simulationRuleEnums) {
		ObservableList<AbstractRule> ruleList = new ObservableListWrapper<>(new ArrayList<>(Factory.getFactory().createRules().values()));
		
		List<AbstractRule> toBeRemoved = new ArrayList<>();
		for (AbstractRule rule : ruleList) {
			if (!simulationRuleEnums.contains(rule.getRuleEnum())) {
				toBeRemoved.add(rule);
			}
		}
		
		ruleList.removeAll(toBeRemoved);
		
		return ruleList;
	}
	
	public static ObservableList<HomeProcess> getAvailableProcesses(Simulation simulation) {
		List<HomeProcess> availableProcesses = new ArrayList<>();
		List<ApplianceEnum> testApplianceList;
		
		List<ApplianceEnum> processApplianceEnums = new ArrayList<>();
		for (HomeProcess homeProcess : Main.getProcesses()) {
			List<ApplianceEnum> simulationApplianceEnums = simulation.getApplianceEnums();
			
			for (AbstractAction action : homeProcess.getActions()) {
				processApplianceEnums.add(action.getAppliance().getApplianceEnum());
			}
			
			testApplianceList = processApplianceEnums.stream().filter(simulationApplianceEnums::contains).collect(Collectors.toList());
			if (testApplianceList.size() == processApplianceEnums.size()) {
				availableProcesses.add(homeProcess);
			}
			
			processApplianceEnums.clear();
		}
		
		return new ObservableListWrapper<>(availableProcesses);
	}
	
	public static ObservableList<AbstractAppliance> getAvailableAppliances(List<ApplianceEnum> applianceEnums) {
		ObservableList<AbstractAppliance> applianceList = new ObservableListWrapper<>(new ArrayList<>(Factory.getFactory().createAppliances().values
				()));
		
		List<AbstractAppliance> toBeRemoved = new ArrayList<>();
		for (AbstractAppliance appliance : applianceList) {
			if (!applianceEnums.contains(appliance.getApplianceEnum())) {
				toBeRemoved.add(appliance);
			}
		}
		
		applianceList.removeAll(toBeRemoved);
		
		return applianceList;
	}
	
	public static void setAppliances(Simulation simulation, Collection<AbstractAppliance> appliances) {
		for(RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
			for(AbstractAction action : ruleProcessPair.getProcess().getActions()) {
				for(AbstractAppliance appliance : appliances) {
					if(action.getAppliance().getApplianceEnum().equals(appliance.getApplianceEnum())) {
						action.setAppliance(appliance);
					}
				}
			}
		}
	}
	
	private static List<RuleEnum> getRuleEnums(Simulation simulation) {
		List<RuleEnum> simulationRuleEnums = new ArrayList<>();
		
		List<AbstractSensor> simulationSensors = Factory.createSensors(simulation, new HomeState());
		for (AbstractSensor sensor : simulationSensors) {
			simulationRuleEnums.add(sensor.getRuleEnum());
		}
		
		List<AbstractAppliance> appliances = new ArrayList<>(Factory.createAppliances().values());
		for(AbstractAppliance appliance : appliances) {
			if(simulation.getApplianceEnums().contains(appliance.getApplianceEnum()) && appliance.getRuleEnum() !=
					null) {
				simulationRuleEnums.add(appliance.getRuleEnum());
			}
		}
		
		return simulationRuleEnums;
	}
	
	public void switchPane(String path) {
		try {
			if (Main.getSimulationRunner() != null && !Main.getSimulationRunner().isPaused()) {
				Main.getSimulationRunner().pause();
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			AnchorPane processPane = FXMLLoader.load(loader.getLocation());
			
			BorderPane border = Main.getRoot();
			border.setCenter(processPane);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RulesEngineSchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToSimulationPage(String path, Simulation simulation) {
		try {
			if (Main.getSimulationRunner() != null && !Main.getSimulationRunner().isPaused()) {
				Main.getSimulationRunner().pause();
			}
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			AnchorPane processPane = loader.load();
			
			ISimulationController controller = loader.getController();
			controller.setSimulation(simulation);
			controller.postInitialize();
			
			BorderPane border = Main.getRoot();
			border.setCenter(processPane);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RulesEngineSchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToEditProcessPage(HomeProcess process) {
		try {
			if (Main.getSimulationRunner() != null && !Main.getSimulationRunner().isPaused()) {
				Main.getSimulationRunner().pause();
			}
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(editProcessPagePath));
			AnchorPane processPane = loader.load();
			
			ProcessEditController controller = loader.getController();
			controller.setHomeProcess(process);
			controller.postInitialize();
			
			if (!controller.getSimulations().isEmpty()) {
				if (!showConfirmationDialog("There are existing simulations which use process " + process.getName() +
						". Choice of appliances in editing will be limited by them and will affect all of them. Do " +
						"you want to continue?")) {
					return;
				}
			}
			
			BorderPane border = Main.getRoot();
			border.setCenter(processPane);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RulesEngineSchedulerException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public void switchToEditRulePage(HomeRule homeRule) {
		try {
			if (Main.getSimulationRunner() != null && !Main.getSimulationRunner().isPaused()) {
				Main.getSimulationRunner().pause();
			}
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(editRulePagePath));
			AnchorPane processPane = loader.load();
			
			RuleEditController controller = loader.getController();
			controller.setHomeRule(homeRule);
			controller.postInitialize();
			
			if (!controller.getSimulations().isEmpty()) {
				if (!showConfirmationDialog("There are existing simulations which use rule " + homeRule.getName() +
						". Choice of appliances in editing will be limited by them and will affect all of them. Do " +
						"you want to continue?")) {
					return;
				}
			}
			
			BorderPane border = Main.getRoot();
			border.setCenter(processPane);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RulesEngineSchedulerException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public void showWarningDialog(String title, String header, String text) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		
		alert.showAndWait();
	}
	
	public boolean showConfirmationDialog(String text) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES) {
			return true;
		} else {
			return false;
		}
	}
	
}
