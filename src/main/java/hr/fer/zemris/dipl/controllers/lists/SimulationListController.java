package hr.fer.zemris.dipl.controllers.lists;

import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.controllers.SimulationParametersController;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.controllers.SimulationViewController;
import hr.fer.zemris.dipl.model.Simulation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimulationListController implements Initializable {

    @FXML
    private TableView<Simulation> tableView;

    @FXML
    private TableColumn<Simulation, String> nameColumn;

    @FXML
    private TableColumn<Simulation, Simulation> viewColumn;

    @FXML
    private TableColumn<Simulation, Simulation> runColumn;

    @FXML
    private TableColumn<Simulation, Simulation> removeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        removeColumn.setCellFactory(param -> new ButtonTableCell<Simulation>("X") {
            @Override
            public void event(Simulation simulation) {
                Main.getSimulations().remove(simulation);
            }
        });
        
        runColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
	    runColumn.setCellFactory(param -> new ButtonTableCell<Simulation>("Run") {
		    @Override
		    public void event(Simulation simulation) {
			    if (simulation.getRuleProcessPairs().isEmpty()) {
				    if (Utility.getUtility().showConfirmationDialog("No rule process pairs added to simulation. Do you want to " +
						    "continue to run simulation with no automation?")) {
					    return;
				    }
			    }
			    Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_parameters.fxml", simulation);
		    }
	    });
    
        viewColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        viewColumn.setCellFactory(param -> new ButtonTableCell<Simulation>("View") {
            @Override
            public void event(Simulation simulation) {
	            Utility.getUtility().switchToSimulationPage("/fxml/pages/simulation_view.fxml", simulation);
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.setItems(Main.getSimulations());
    }

    @FXML
    private void switchToCreateNewSimulation() {
        Utility.getUtility().switchPane("/fxml/pages/new_simulation.fxml");
    }
    
    @FXML
	private void back() {
    	Utility.getUtility().switchPane("/fxml/pages/main.fxml");
    }

}
