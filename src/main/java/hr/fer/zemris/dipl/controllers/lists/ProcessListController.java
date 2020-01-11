package hr.fer.zemris.dipl.controllers.lists;

import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.HomeProcess;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Domagoj on 22.4.2017..
 */
public class ProcessListController implements Initializable {

    @FXML
    private TableView<HomeProcess> tableView;

    @FXML
    private TableColumn<HomeProcess, String> nameColumn;

    @FXML
    private TableColumn<HomeProcess, String> descriptionColumn;

    @FXML
    private TableColumn<HomeProcess, HomeProcess> removeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        removeColumn.setCellFactory(param -> new ButtonTableCell<HomeProcess>("X") {
            @Override
            public void event(HomeProcess process) {
                if(isDeletionValid(process)) {
                    Main.getProcesses().remove(process);
                }
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(Main.getProcesses());
        tableView.setRowFactory( tv -> {
            TableRow<HomeProcess> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    HomeProcess rowData = row.getItem();
                    Utility.getUtility().switchToEditProcessPage(rowData);
                }
            });
            return row ;
        });
    }
    
    private boolean isDeletionValid(HomeProcess process) {
        boolean valid = true;
        
        for(Simulation simulation : Main.getSimulations()) {
            for(RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
                if(process.equals(ruleProcessPair.getProcess())) {
                    valid = false;
                    Utility.getUtility().showWarningDialog("Unable to delete", "Unable to delete \"" +
                            process.getName() + "\" ", "Simulation \"" + simulation.getName() + "\" contains process" +
                            " \"" + process.getName() + "\". Remove it from simulation to enable deletion.");
                }
            }
        }
        return valid;
    }

    @FXML
    void switchToCreateNewProcess(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/new_process.fxml");
    }

    @FXML
    public void back() {
        Utility.getUtility().switchPane("/fxml/pages/main.fxml");
    }
}
