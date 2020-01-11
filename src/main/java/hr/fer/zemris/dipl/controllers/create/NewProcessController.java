package hr.fer.zemris.dipl.controllers.create;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.Factory;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.controllers.ISimulationController;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.gui.decorated.DoubleValueSpinner;
import hr.fer.zemris.dipl.gui.decorated.EditableValueTableCell;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.actions.InstantAction;
import hr.fer.zemris.dipl.model.HomeProcess;
import hr.fer.zemris.dipl.model.actions.TemporalAction;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.rules.conditions.BooleanCondition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Domagoj on 20.4.2017..
 */
public class NewProcessController implements Initializable, ISimulationController {
	
	@FXML
	protected TextField processNameField;
	
	@FXML
	protected TextField processDescField;
	
	@FXML
	protected Label warningMessage;
	
	@FXML
	protected ChoiceBox<AbstractAppliance> applianceChoiceBox;
	
	@FXML
	private ChoiceBox<AbstractAction> actionChoiceBox;
	
	@FXML
	private Pane valuePane;
	
	private ChoiceBox<BooleanCondition> booleanActionChoiceBox = new ChoiceBox<>();
	
	private DoubleValueSpinner numericActionSpinner = new DoubleValueSpinner();
	
	@FXML
	protected TableView<AbstractAction> actionTableView;
	
	@FXML
	private TableColumn<AbstractAction, Number> numeroColumn;
	
	@FXML
	private TableColumn<AbstractAction, String> actionColumn;
	
	@FXML
	private TableColumn<AbstractAction, String> valueColumn;
	
	@FXML
	private TableColumn<AbstractAction, AbstractAction> removeColumn;
	
	@FXML
	private ChoiceBox<HomeProcess> processChoiceBox;
	
	protected ObservableList<AbstractAction> actions = new ObservableListWrapper<>(new ArrayList<>());
	
	private Simulation simulation;
	
	@Override
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
		processChoiceBox.setItems(Utility.getAvailableProcesses(simulation));
	}
	
	@Override
	public void postInitialize() throws IOException {
		applianceChoiceBox.setItems(Utility.getAvailableAppliances(simulation.getApplianceEnums()));
		applianceChoiceBox.setValue(applianceChoiceBox.getItems().get(0));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeActionTableView();
		initializeApplianceChoiceBox();
		initializeActionChoiceBox();
		initializeValueFields();
		initializeProcessChoiceBox();
	}
	
	protected void initializeActionTableView() {
		removeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		removeColumn.setCellFactory(param -> new ButtonTableCell<AbstractAction>("X") {
			@Override
			public void event(AbstractAction process) {
				actions.remove(process);
			}
		});
		
		numeroColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(actionTableView.getItems().indexOf(c.getValue()) +
				1));
		actionColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValueString()));
		valueColumn.setCellFactory(column -> new EditableValueTableCell<AbstractAction>() {
			@Override
			protected Double getNumericItemValue() {
				return ((TemporalAction) getEditableItem()).getValue();
			}
			
			@Override
			protected BooleanCondition getBooleanItemValue() {
				return BooleanCondition.getBooleanCondition(((InstantAction) getEditableItem()).getValue());
			}
			
			@Override
			protected void setItemValue(Double value) {
				((TemporalAction) getEditableItem()).setValue(value);
			}
			
			@Override
			protected void setItemValue(BooleanCondition value) {
				((InstantAction) getEditableItem()).setValue(BooleanCondition.getBoolean(value));
			}
			
			@Override
			protected Double getMinSpinnerValue() {
				return ((TemporalAction) getEditableItem()).getMinValue();
			}
			
			@Override
			protected Double getMaxSpinnerValue() {
				return ((TemporalAction) getEditableItem()).getMaxValue();
			}
			
			@Override
			protected Double getStepSpinnerValue() {
				return ((TemporalAction) getEditableItem()).getStepValue();
			}
		});
		actionTableView.setItems(actions);
	}
	
	protected void initializeApplianceChoiceBox() {
		Map<String, AbstractAppliance> applianceMap = Factory.getFactory().createAppliances();
		ObservableList<AbstractAppliance> applianceList = new ObservableListWrapper<>(new ArrayList<>(applianceMap.values
				()));
		
		applianceChoiceBox.setConverter(new StringConverter<AbstractAppliance>() {
			@Override
			public String toString(AbstractAppliance appliance) {
				return appliance.getName();
			}
			
			@Override
			public AbstractAppliance fromString(String string) {
				return applianceMap.get(string);
			}
		});
		
		applianceChoiceBox.setItems(applianceList);
		applianceChoiceBox.setValue(applianceChoiceBox.getItems().get(0));
	}
	
	protected void initializeActionChoiceBox() {
		Map<String, AbstractAction> actionMap = new HashMap<>();
		for (AbstractAction action : actions) {
			actionMap.put(action.getName(), action);
		}
		
		actionChoiceBox.setConverter(new StringConverter<AbstractAction>() {
			@Override
			public String toString(AbstractAction appliance) {
				return appliance.getName();
			}
			
			@Override
			public AbstractAction fromString(String string) {
				return actionMap.get(string);
			}
		});
	}
	
	
	@FXML
	private void setUpActionChoiceBox() {
		AbstractAppliance appliance = applianceChoiceBox.getValue();
		//when changing values in ChoiceBox method is triggered, but value is always null at first
		if (appliance != null) {
			ObservableList<AbstractAction> actions = new ObservableListWrapper<>(appliance.getActions());
			
			actionChoiceBox.setItems(actions);
			actionChoiceBox.setValue(actionChoiceBox.getItems().get(0));
		}
		
	}
	
	protected void initializeValueFields() {
		booleanActionChoiceBox.setItems(new ObservableListWrapper<>(Arrays.asList(BooleanCondition.values())));
		booleanActionChoiceBox.setValue(booleanActionChoiceBox.getItems().get(0));
		
		numericActionSpinner.setMaxWidth(100);
	}
	
	private void initializeProcessChoiceBox() {
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
		processChoiceBox.setItems(Main.getProcesses());
	}
	
	@FXML
	private void setFieldsToExistingProcess() throws CloneNotSupportedException {
		HomeProcess process = processChoiceBox.getValue();
		if(process != null) {
			HomeProcess clone = (HomeProcess) process.clone();
			processNameField.setText(clone.getName() + " copy");
			
			for(AbstractAction action : clone.getActions()) {
				for(AbstractAppliance appliance : applianceChoiceBox.getItems()) {
					if(action.getAppliance().equals(appliance)) {
						action.setAppliance(appliance);
					}
				}
			}
			actions.clear();
			actions.addAll(clone.getActions());
		}
	}
	
	@FXML
	private void setValueField() {
		AbstractAction action = actionChoiceBox.getValue();
		valuePane.getChildren().clear();
		
		if (action instanceof InstantAction) {
			valuePane.getChildren().add(booleanActionChoiceBox);
		} else if (action instanceof TemporalAction) {
			TemporalAction temporalAction = (TemporalAction) action;
			numericActionSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(temporalAction
					.getMinValue(), temporalAction.getMaxValue(), temporalAction.getValue(),
					temporalAction.getStepValue()));
			DoubleValueSpinner.setValueFactoryStringConverter(numericActionSpinner);
			valuePane.getChildren().add(numericActionSpinner);
		}
	}
	
	@FXML
	private void addAction() throws CloneNotSupportedException {
		AbstractAction action = actionChoiceBox.getValue();
		
		if (action instanceof InstantAction) {
			((InstantAction) action).setValue(BooleanCondition.getBoolean(booleanActionChoiceBox.getValue()));
		} else if (action instanceof TemporalAction) {
			((TemporalAction) action).setValue(numericActionSpinner.getValue());
		}
		AbstractAction actionClone = (AbstractAction) action.clone();
		actions.add(actionClone);
	}
	
	@FXML
	public void create() {
		String nameInput = processNameField.getText();
		String descInput = processDescField.getText();
		if (nameInput.length() == 0) {
			warningMessage.setText("Please set process name...");
			return;
		}
		
		if(actions.isEmpty()) {
			Utility.getUtility().showWarningDialog("No actions", "No added actions in process", "Please add at " +
					"least one action to process before creating it.");
			return;
		}
		
		List<HomeProcess> processes = Main.getProcesses();
		for (HomeProcess process : processes) {
			if (nameInput.equals(process.getName())) {
				warningMessage.setText("Process with chosen name already exists...");
				return;
			}
		}
		
		Factory.getFactory().createProcess(nameInput, descInput, actions);
		
		if (simulation != null) {
			Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_rule_process.fxml", simulation);
		} else {
			Utility.getUtility().switchPane("/fxml/pages/processes.fxml");
		}
		
	}
	
	@FXML
	public void back() {
		if (simulation == null) {
			Utility.getUtility().switchPane("/fxml/pages/processes.fxml");
		} else {
			Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_rule_process.fxml", simulation);
		}
		
	}
}
