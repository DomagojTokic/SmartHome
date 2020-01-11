package hr.fer.zemris.dipl.controllers;

import hr.fer.zemris.dipl.general.Utility;
import javafx.fxml.FXML;

/**
 * Created by Domagoj on 1.6.2017..
 */
public class MainPageController {
	
	@FXML
	private void goToSimulations() {
		Utility.getUtility().switchPane("/fxml/pages/simulations.fxml");
	}
	
	@FXML
	private void goToRules() {
		Utility.getUtility().switchPane("/fxml/pages/rules.fxml");
	}
	
	@FXML
	private void goToProcesses() {
		Utility.getUtility().switchPane("/fxml/pages/processes.fxml");
	}
	
	@FXML
	private void goToCreateSimulation() {
		Utility.getUtility().switchPane("/fxml/pages/new_simulation.fxml");
	}
	
	@FXML
	private void goToCreateRule() {
		Utility.getUtility().switchPane("/fxml/pages/new_rule.fxml");
	}
	
	@FXML
	private void goToCreateProcess() {
		Utility.getUtility().switchPane("/fxml/pages/new_process.fxml");
	}
}
