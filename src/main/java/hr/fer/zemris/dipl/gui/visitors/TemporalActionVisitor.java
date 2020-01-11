package hr.fer.zemris.dipl.gui.visitors;

import hr.fer.zemris.dipl.model.actions.TemporalAction;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Created by Domagoj on 7.6.2017..
 */
public class TemporalActionVisitor {
	
	private TextArea processTextArea;
	private TemporalAction action;
	
	public TemporalActionVisitor(TemporalAction action, TextArea processTextArea) {
		this.processTextArea = processTextArea;
		this.action = action;
	}
	
	public void visitStart() {
		Platform.runLater(() -> processTextArea.appendText("\tAction " + action.getName() + " to value " + action
				.getValueString() + " has started.\n"));
	}
	
	public void visitEnd() {
		Platform.runLater(() -> processTextArea.appendText("\tAction " + action.getName() + " to value " + action
				.getValueString() + " has ended.\n"));
	}
	
}
