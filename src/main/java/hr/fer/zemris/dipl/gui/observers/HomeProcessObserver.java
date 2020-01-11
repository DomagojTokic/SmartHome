package hr.fer.zemris.dipl.gui.observers;

import hr.fer.zemris.dipl.model.HomeProcess;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Created by Domagoj on 2.6.2017..
 */
public class HomeProcessObserver implements IObserver {
	
	private HomeProcess homeProcess;
	
	private TextArea processTextArea;
	
	public HomeProcessObserver(HomeProcess homeProcess, TextArea processTextArea) {
		this.homeProcess = homeProcess;
		this.processTextArea = processTextArea;
	}
	
	@Override
	public void update() {
		Platform.runLater(() -> processTextArea.appendText("Started \"" + homeProcess.getName() + "\" process...\n"));
	}
}
