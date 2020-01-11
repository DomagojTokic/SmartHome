package hr.fer.zemris.dipl.gui.decorated;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 * Created by Domagoj on 3.5.2017..
 */
public abstract class ButtonTableCell<T> extends TableCell<T, T> {
	
	private Button button;
	
	public ButtonTableCell(String buttonText) {
		button = new Button(buttonText);
	}
	
	@Override
	protected void updateItem(T object, boolean empty) {
		super.updateItem(object, empty);
		
		if (object == null) {
			setGraphic(null);
			return;
		}
		
		setGraphic(button);
		button.setOnAction(event -> event(object));
	}
	
	public abstract void event(T object);

}
