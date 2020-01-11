package hr.fer.zemris.dipl.controllers;

import hr.fer.zemris.dipl.general.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

/**
 * Created by Domagoj on 22.4.2017..
 */
public class MenuController {

    private String aboutHeader = "Smart Home Simulation v1.0";
    
    private String aboutContent = "SmartHome is a simulation of smart home which simulates whole process of " +
            "appliance actions activated by certain sensor readings.";
    
    @FXML
    private void switchToSimulations(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/simulations.fxml");
    }

    @FXML
    private void switchToCreateNewSimulation(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/new_simulation.fxml");
    }

    @FXML
    private void switchToCreateNewProcess(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/new_process.fxml");
    }

    @FXML
    private void switchToCreateNewRule(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/new_rule.fxml");
    }

    @FXML
    private void switchToRules(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/rules.fxml");
    }

    @FXML
    private void switchToProcesses(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/processes.fxml");
    }
    
    @FXML
    private void switchToMain(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/main.fxml");
    }
    
    @FXML
    private void showAboutScreen() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(aboutHeader);
        alert.setContentText(aboutContent);
        
        alert.showAndWait();
    }
}
