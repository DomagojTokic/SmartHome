package hr.fer.zemris.dipl.gui.visitors;

import hr.fer.zemris.dipl.model.actions.InstantAction;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Created by Domagoj on 17.6.2017..
 */
public class InstantActionVisitor {
	
	private TextArea processTextArea;
	private InstantAction action;
	
	public InstantActionVisitor(InstantAction action, TextArea processTextArea) {
		this.processTextArea = processTextArea;
		this.action = action;
	}
	
	
	public void visit() {
		Platform.runLater(() -> processTextArea.appendText("\tAction " + action.getName() + " to value " + action
				.getValueString() + " executed.\n"));
	}
	
}
