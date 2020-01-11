package hr.fer.zemris.dipl.controllers.edit;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.controllers.create.NewRuleController;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.model.HomeRule;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.rules.AbstractRule;
import hr.fer.zemris.dipl.model.rules.BooleanRule;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Domagoj on 5.7.2017..
 */
public class RuleEditController extends NewRuleController {
	
	private HomeRule originalHomeRule;
	private HomeRule editHomeRule;
	
	private List<Simulation> simulations;
	
	public List<Simulation> getSimulations() {
		return simulations;
	}
	
	public void setHomeRule(HomeRule homeRule) throws CloneNotSupportedException {
		this.originalHomeRule = homeRule;
		editHomeRule = (HomeRule) homeRule.clone();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTableView();
		initializeChoiceBox();
		initializeNumericValueFields();
		initializeBooleanValueFields();
	}
	
	@Override
	public void postInitialize() {
		ruleNameField.setText("Edit " + originalHomeRule.getName());
		setSimulations();
		conditions = new ObservableListWrapper<>(editHomeRule.getConditions());
		
		List<AbstractRule> ruleChoiceBoxList = Utility.getAvailableConditions(simulations);
		clearUsedBooleanConditions(ruleChoiceBoxList);
		
		ruleChoiceBox.setItems(new ObservableListWrapper<>(ruleChoiceBoxList));
		if(!ruleChoiceBox.getItems().isEmpty()) {
			ruleChoiceBox.setValue(ruleChoiceBox.getItems().get(0));
		}
		
		ruleNameField.setText(originalHomeRule.getName());
		ruleDescField.setText(originalHomeRule.getDescription());
		
		conditionTableView.setItems(conditions);
	}
	
	private void clearUsedBooleanConditions(List<AbstractRule> ruleChoiceBoxList) {
		List<AbstractRule> toBeRemoved = new ArrayList<>();
		
		for(AbstractRule condition : ruleChoiceBoxList) {
			if(condition instanceof BooleanRule) {
				for(AbstractRule usedCondition : conditions) {
					if(condition.getRuleEnum().equals(usedCondition.getRuleEnum())) {
						toBeRemoved.add(condition);
					}
				}
			}
		}
		ruleChoiceBoxList.removeAll(toBeRemoved);
	}
	
	public void setSimulations() {
		simulations = new ArrayList<>();
		
		for(Simulation simulation : Main.getSimulations()) {
			for(RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
				if(ruleProcessPair.getRule().equals(originalHomeRule)) {
					simulations.add(simulation);
				}
			}
		}
	}
	
	@FXML
	private void edit() {
		String nameInput = ruleNameField.getText();
		String descInput = ruleDescField.getText();
		if (nameInput.length() == 0) {
			warningMessage.setText("Please set process name...");
			return;
		}
		
		if(editHomeRule.getConditions().isEmpty()) {
			Utility.getUtility().showWarningDialog("No conditions", "No added conditions in process", "Please add at " +
					"least one condition to process before editing.");
			return;
		}
		
		List<HomeRule> rules = Main.getRules();
		for (HomeRule rule : rules) {
			if (nameInput.equals(rule.getName()) && !nameInput.equals(originalHomeRule.getName())) {
				warningMessage.setText("Rule with chosen name already exists...");
				return;
			}
		}
		
		originalHomeRule.setName(nameInput);
		originalHomeRule.setDescription(descInput);
		originalHomeRule.setConditions(editHomeRule.getConditions());
		
		Utility.getUtility().switchPane("/fxml/pages/rules.fxml");
	}
}
