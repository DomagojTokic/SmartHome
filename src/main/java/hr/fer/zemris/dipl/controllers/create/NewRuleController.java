package hr.fer.zemris.dipl.controllers.create;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.Factory;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.controllers.ISimulationController;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.gui.decorated.DoubleValueSpinner;
import hr.fer.zemris.dipl.gui.decorated.EditableValueTableCell;
import hr.fer.zemris.dipl.model.HomeRule;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.interfaces.TableViewEditable;
import hr.fer.zemris.dipl.model.rules.BooleanRule;
import hr.fer.zemris.dipl.model.rules.NumericRule;
import hr.fer.zemris.dipl.model.rules.AbstractRule;
import hr.fer.zemris.dipl.model.rules.conditions.BooleanCondition;
import hr.fer.zemris.dipl.model.rules.conditions.NumericCondition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Domagoj on 20.4.2017..
 */
public class NewRuleController implements Initializable, ISimulationController {
	
	@FXML
	protected TextField ruleNameField;
	
	@FXML
	protected TextField ruleDescField;
	
	@FXML
	protected Label warningMessage;
	
	@FXML
	protected ChoiceBox<AbstractRule> ruleChoiceBox;
	
	@FXML
	private Pane conditionPane;
	
	@FXML
	private Pane conditionValuePane;
	
	@FXML
	protected TableView<AbstractRule> conditionTableView;
	
	@FXML
	private TableColumn<AbstractRule, String> conditionNameColumn;
	
	@FXML
	private TableColumn<AbstractRule, String> conditionValueColumn;
	
	@FXML
	private TableColumn<AbstractRule, AbstractRule> removeColumn;
	
	@FXML
	private ChoiceBox<HomeRule> existingRuleChoiceBox;
	
	private ChoiceBox<BooleanCondition> booleanConditionChoiceBox = new ChoiceBox<>();
	
	private ChoiceBox<NumericCondition> numericConditionChoiceBox = new ChoiceBox<>();
	
	private DoubleValueSpinner valueSpinner = new DoubleValueSpinner();
	
	private Simulation simulation;
	
	protected ObservableList<AbstractRule> conditions = new ObservableListWrapper<>(new ArrayList<>());
	
	public NewRuleController() {
		ObservableList<NumericCondition> numericConditions = new ObservableListWrapper<>
				(Arrays.asList(NumericCondition.values()));
		numericConditionChoiceBox.setItems(numericConditions);
		
		ObservableList<BooleanCondition> booleanConditions = new ObservableListWrapper<>
				(Arrays.asList(BooleanCondition.values()));
		booleanConditionChoiceBox.setItems(booleanConditions);
	}
	
	
	@Override
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
		existingRuleChoiceBox.setItems(Utility.getAvailableRules(simulation));
	}
	
	@Override
	public void postInitialize() throws IOException {
		ruleChoiceBox.setItems(Utility.getAvailableConditions(simulation));
		ruleChoiceBox.setValue(ruleChoiceBox.getItems().get(0));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTableView();
		initializeChoiceBox();
		initializeNumericValueFields();
		initializeBooleanValueFields();
		initializeExistingRuleChoiceBox();
	}
	
	protected void initializeTableView() {
		removeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		removeColumn.setCellFactory(param -> new ButtonTableCell<AbstractRule>("X") {
			@Override
			public void event(AbstractRule rule) {
				if (rule instanceof BooleanRule) {
					ruleChoiceBox.getItems().add(rule);
					if(ruleChoiceBox.getItems().size() == 1) {
						ruleChoiceBox.setValue(ruleChoiceBox.getItems().get(0));
					}
				}
				conditions.remove(rule);
			}
		});
		
		conditionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		conditionValueColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getConditionString()));
		conditionValueColumn.setCellFactory(column -> new EditableValueTableCell<AbstractRule>() {
			
			@Override
			protected Double getNumericItemValue() {
				return ((NumericRule) getEditableItem()).getValue();
			}
			
			@Override
			protected BooleanCondition getBooleanItemValue() {
				return ((BooleanRule) getEditableItem()).getCondition();
			}
			
			@Override
			protected void setItemValue(Double value) {
				((NumericRule) getEditableItem()).setValue(value);
			}
			
			@Override
			protected void setItemValue(BooleanCondition value) {
				((BooleanRule) getEditableItem()).setCondition(value);
			}
			
			@Override
			protected Double getMinSpinnerValue() {
				return ((NumericRule) getEditableItem()).getMinValue();
			}
			
			@Override
			protected Double getMaxSpinnerValue() {
				return ((NumericRule) getEditableItem()).getMaxValue();
			}
			
			@Override
			protected Double getStepSpinnerValue() {
				return ((NumericRule) getEditableItem()).getStepValue();
			}
		});
		conditionTableView.setItems(conditions);
	}
	
	protected void initializeChoiceBox() {
		Map<String, AbstractRule> ruleMap = Factory.getFactory().createRules();
		ObservableList<AbstractRule> ruleList = new ObservableListWrapper<>(new ArrayList<>(ruleMap.values()));
		
		ruleChoiceBox.setConverter(new StringConverter<AbstractRule>() {
			@Override
			public String toString(AbstractRule rule) {
				return rule.getName();
			}
			
			@Override
			public AbstractRule fromString(String string) {
				return ruleMap.get(string);
			}
		});
		
		ruleChoiceBox.setItems(ruleList);
		ruleChoiceBox.setValue(ruleChoiceBox.getItems().get(0));
	}
	
	protected void initializeNumericValueFields() {
		numericConditionChoiceBox.setConverter(new StringConverter<NumericCondition>() {
			@Override
			public String toString(NumericCondition object) {
				return object.toString();
			}
			
			@Override
			public NumericCondition fromString(String string) {
				return NumericCondition.valueOf(string);
			}
		});
		
		valueSpinner.setMaxWidth(100);
	}
	
	protected void initializeBooleanValueFields() {
		booleanConditionChoiceBox.setConverter(new StringConverter<BooleanCondition>() {
			@Override
			public String toString(BooleanCondition object) {
				return object.toString();
			}
			
			@Override
			public BooleanCondition fromString(String string) {
				return BooleanCondition.valueOf(string);
			}
		});
		
		booleanConditionChoiceBox.setValue(booleanConditionChoiceBox.getItems().get(0));
	}
	
	private void initializeExistingRuleChoiceBox() {
		existingRuleChoiceBox.setConverter(new StringConverter<HomeRule>() {
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
		existingRuleChoiceBox.setItems(Main.getRules());
	}
	
	@FXML
	private void setFieldsToExistingRule() throws CloneNotSupportedException {
		HomeRule rule = existingRuleChoiceBox.getValue();
		if(rule != null) {
			HomeRule clone = (HomeRule) rule.clone();
			ruleNameField.setText(clone.getName() + " copy");
			
			List<AbstractRule> toBeRemoved = new ArrayList<>();
			for(AbstractRule cloneRule : clone.getConditions()) {
				for(AbstractRule choiceBoxRule : ruleChoiceBox.getItems()) {
					if(cloneRule instanceof BooleanRule && cloneRule.getRuleEnum().equals(choiceBoxRule.getRuleEnum())) {
						toBeRemoved.add(choiceBoxRule);
					}
				}
			}
			ruleChoiceBox.getItems().removeAll(toBeRemoved);
			
			conditions.clear();
			conditions.addAll(clone.getConditions());
		}
	}
	
	@FXML
	public void create() {
		String nameInput = ruleNameField.getText();
		String descInput = ruleDescField.getText();
		if (nameInput.length() == 0) {
			warningMessage.setText("Please set rule name...");
			return;
		}
		
		List<HomeRule> rules = Main.getRules();
		for (HomeRule rule : rules) {
			if (nameInput.equals(rule.getName())) {
				warningMessage.setText("Rule with chosen name already exists...");
				return;
			}
		}
		
		if(conditions.isEmpty()) {
			Utility.getUtility().showWarningDialog("No conditions", "No added conditions in rule", "Please add " +
					"at least one condition to rule before creating it.");
			return;
		}
		
		Factory.getFactory().createRule(nameInput, descInput, conditions);
		
		if (simulation != null) {
			Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_rule_process.fxml", simulation);
		} else {
			Utility.getUtility().switchPane("/fxml/pages/rules.fxml");
		}
	}
	
	@FXML
	public void setRuleValueFields() {
		conditionPane.getChildren().clear();
		conditionValuePane.getChildren().clear();
		
		Object selectedRule = ruleChoiceBox.getValue();
		
		if (selectedRule instanceof BooleanRule) {
			setBooleanRuleValueField();
		} else if (selectedRule instanceof NumericRule) {
			setNumericRuleValueField((NumericRule) selectedRule);
		}
	}
	
	private void setBooleanRuleValueField() {
		conditionPane.getChildren().add(booleanConditionChoiceBox);
	}
	
	private void setNumericRuleValueField(NumericRule rule) {
		conditionPane.getChildren().add(numericConditionChoiceBox);
		numericConditionChoiceBox.setValue(numericConditionChoiceBox.getItems().get(0));
		valueSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(rule.getMinValue(), rule.getMaxValue(),
				rule.getValue(), rule.getStepValue()));
		DoubleValueSpinner.setValueFactoryStringConverter(valueSpinner);
		conditionValuePane.getChildren().add(valueSpinner);
	}
	
	@FXML
	public void addCondition() throws CloneNotSupportedException {
		AbstractRule rule = ruleChoiceBox.getValue();
		if(rule == null) {
			Utility.getUtility().showWarningDialog("Warning", "No condition selected", "Please select a condition " +
					"before adding it to rule.");
			return;
		}
		
		if (rule instanceof BooleanRule) {
			BooleanCondition condition = booleanConditionChoiceBox.getValue();
			((BooleanRule) rule).setCondition(condition);
		}
		
		if (rule instanceof NumericRule) {
			NumericRule ruleClone = (NumericRule) rule.clone();
			ruleChoiceBox.getItems().add(ruleClone);
			
			NumericCondition condition = numericConditionChoiceBox.getValue();
			double value = valueSpinner.getValue();
			((NumericRule) rule).setCondition(condition);
			((NumericRule) rule).setValue(value);
		}
		conditions.add(rule);
		ruleChoiceBox.getItems().remove(rule);
		if(!ruleChoiceBox.getItems().isEmpty()) {
			ruleChoiceBox.setValue(ruleChoiceBox.getItems().get(0));
		}
	}
	
	@FXML
	private void back() {
		if (simulation == null) {
			Utility.getUtility().switchPane("/fxml/pages/rules.fxml");
		} else {
			Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_rule_process.fxml", simulation);
		}
	}
}
