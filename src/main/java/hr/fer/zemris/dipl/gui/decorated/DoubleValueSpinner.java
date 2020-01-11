package hr.fer.zemris.dipl.gui.decorated;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.util.StringConverter;

/**
 * Created by Domagoj on 15.6.2017..
 */
public class DoubleValueSpinner extends Spinner<Double> {
	
	public DoubleValueSpinner() {
		convertToDoubleValueSpinner(this);
	}
	
	public static Spinner<Double> convertToDoubleValueSpinner(Spinner<Double> spinner) {
		spinner.setEditable(true);
		
		spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue && !spinner.getEditor().getText().isEmpty() && !spinner.getEditor().getText().equals("-")) {
				spinner.increment(0);
			}
		});
		
		spinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("((-)|[-]?\\d+)|([-]?\\d+\\.\\d+)|([-]?\\d+,\\d+)|([-]?\\d+\\.)|([-]?\\d+,)") &&
					!newValue.isEmpty()) {
				spinner.getEditor().setText(newValue.substring(0, newValue.length() - 1));
			}
		});
		if(spinner.getValueFactory() != null) {
			setValueFactoryStringConverter(spinner);
		}
		
		return spinner;
	}
	
	public static void setValueFactoryStringConverter(Spinner<Double> spinner) {
		spinner.getValueFactory().setConverter(new StringConverter<Double>() {
			@Override
			public String toString(Double object) {
				return Double.toString(object);
			}
			
			@Override
			public Double fromString(String string) {
				string = string.replace(',', '.');
				return Double.parseDouble(string);
			}
		});
	}
}
