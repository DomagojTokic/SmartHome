package hr.fer.zemris.dipl.controllers.edit;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.controllers.create.NewProcessController;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.model.HomeProcess;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.*;

/**
 * Created by Domagoj on 5.7.2017..
 */
public class ProcessEditController extends NewProcessController {
	
	private HomeProcess originalHomeProcess;
	private HomeProcess editHomeProcess;
	
	List<Simulation> simulations = new ArrayList<>();
	
	public void setHomeProcess(HomeProcess homeProcess) throws CloneNotSupportedException {
		this.originalHomeProcess = homeProcess;
		this.editHomeProcess = (HomeProcess) homeProcess.clone();
	}
	
	public List<Simulation> getSimulations() {
		return simulations;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeActionTableView();
		initializeApplianceChoiceBox();
		initializeActionChoiceBox();
		initializeValueFields();
	}
	
	@Override
	public void postInitialize() {
		processNameField.setText("Edit " + originalHomeProcess.getName());
		
		applianceChoiceBox.setItems(Utility.getAvailableAppliances(getApplianceEnums()));
		applianceChoiceBox.setValue(applianceChoiceBox.getItems().get(0));
		
		processNameField.setText(editHomeProcess.getName());
		processDescField.setText(editHomeProcess.getDescription());
		
		actions = new ObservableListWrapper<>(editHomeProcess.getActions());
		actionTableView.setItems(actions);
	}
	
	public List<ApplianceEnum> getApplianceEnums() {
		for(Simulation simulation : Main.getSimulations()) {
			for(RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
				if(ruleProcessPair.getProcess().equals(originalHomeProcess)) {
					simulations.add(simulation);
				}
			}
		}
		
		if(simulations.isEmpty()) {
			return Arrays.asList(ApplianceEnum.values());
		} else {
			Set<ApplianceEnum> applianceEnums = new HashSet<>();
			for(Simulation simulation : simulations) {
				applianceEnums.addAll(simulation.getApplianceEnums());
			}
			return new ArrayList<>(applianceEnums);
		}
	}
	
	@FXML
	private void edit() {
		String nameInput = processNameField.getText();
		String descInput = processDescField.getText();
		if (nameInput.length() == 0) {
			warningMessage.setText("Please set process name...");
			return;
		}
		
		if(editHomeProcess.getActions().isEmpty()) {
			Utility.getUtility().showWarningDialog("No actions", "No added actions in process", "Please add at " +
					"least one action to process before editing.");
			return;
		}
		
		List<HomeProcess> processes = Main.getProcesses();
		for (HomeProcess process : processes) {
			if (nameInput.equals(process.getName()) && !nameInput.equals(originalHomeProcess.getName())) {
				warningMessage.setText("Process with chosen name already exists...");
				return;
			}
		}
		
		originalHomeProcess.setName(nameInput);
		originalHomeProcess.setDescription(descInput);
		originalHomeProcess.setActions(editHomeProcess.getActions());
		
		Utility.getUtility().switchPane("/fxml/pages/processes.fxml");
	}
}
