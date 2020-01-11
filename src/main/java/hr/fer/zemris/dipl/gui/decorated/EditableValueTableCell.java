package hr.fer.zemris.dipl.gui.decorated;

import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.model.interfaces.TableViewEditable;
import hr.fer.zemris.dipl.model.rules.conditions.BooleanCondition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

import java.util.Arrays;

/**
 * Created by Domagoj on 14.6.2017..
 */
public abstract class EditableValueTableCell<T extends TableViewEditable> extends TableCell<T, String> {
	
	private ChoiceBox<BooleanCondition> booleanChoiceBox = new ChoiceBox<>();
	
	private DoubleValueSpinner spinner = new DoubleValueSpinner();
	
	private TableViewEditable item;
	
	protected abstract Double getNumericItemValue();
	
	protected abstract BooleanCondition getBooleanItemValue();
	
	protected abstract void setItemValue(Double value);
	
	protected abstract void setItemValue(BooleanCondition value);
	
	protected abstract Double getMinSpinnerValue();
	
	protected abstract Double getMaxSpinnerValue();
	
	protected abstract Double getStepSpinnerValue();
	
	public EditableValueTableCell() {
		booleanChoiceBox.getItems().addAll(Arrays.asList(BooleanCondition.values()));
		booleanChoiceBox.getSelectionModel().selectedIndexProperty().addListener((obs, oldValue, newValue) -> {
			BooleanCondition value = booleanChoiceBox.getItems().get(newValue.intValue());
			processEdit(value);
		});
		
		spinner.getEditor().setOnAction(event -> commitEdit(spinner.getEditor().getText()));
	}
	
	private void processEdit(BooleanCondition value) {
		commitEdit(value.toString());
	}
	
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem());
		setGraphic(null);
	}
	
	@Override
	public void commitEdit(String value) {
		super.commitEdit(value);
		if (item.getValueType().equals(TableViewEditable.ValueType.BOOLEAN)) {
			setItemValue(BooleanCondition.valueOf(value));
			setText(value);
		} else if (item.getValueType().equals(TableViewEditable.ValueType.DOUBLE)) {
			setItemValue(spinner.getValue());
			getTableView().refresh();
		}
		setGraphic(null);
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		
		item = (TableViewEditable) this.getTableRow().getItem();
		String value = getItem();
		if (value != null) {
			if (item.getValueType().equals(TableViewEditable.ValueType.BOOLEAN)) {
				booleanChoiceBox.setValue(getBooleanItemValue());
				setGraphic(booleanChoiceBox);
			} else if (item.getValueType().equals(TableViewEditable.ValueType.DOUBLE)) {
				spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(getMinSpinnerValue(),
						getMaxSpinnerValue(), getNumericItemValue(), getStepSpinnerValue()));
				DoubleValueSpinner.setValueFactoryStringConverter(spinner);
				spinner.getEditor().setText(getNumericItemValue().toString());
				setGraphic(spinner);
				
			}
			setText(null);
		}
	}
	
	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null);
		} else {
			setText(item);
		}
	}
	
	public TableViewEditable getEditableItem() {
		return item;
	}
}
