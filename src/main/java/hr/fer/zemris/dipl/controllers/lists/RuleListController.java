package hr.fer.zemris.dipl.controllers.lists;

import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.gui.decorated.ButtonTableCell;
import hr.fer.zemris.dipl.general.Utility;
import hr.fer.zemris.dipl.model.HomeRule;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
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
public class RuleListController implements Initializable {

    @FXML
    private TableView<HomeRule> tableView;

    @FXML
    private TableColumn<HomeRule, String> nameColumn;

    @FXML
    private TableColumn<HomeRule, String> descriptionColumn;

    @FXML
    private TableColumn<HomeRule, HomeRule> removeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        removeColumn.setCellFactory(param -> new ButtonTableCell<HomeRule>("X") {
            @Override
            public void event(HomeRule rule) {
                if(isDeletionValid(rule)) {
                    Main.getRules().remove(rule);
                }
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    
        tableView.setRowFactory( tv -> {
            TableRow<HomeRule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    HomeRule rowData = row.getItem();
                    Utility.getUtility().switchToEditRulePage(rowData);
                }
            });
            return row ;
        });
        tableView.setItems(Main.getRules());
    }
    
    private boolean isDeletionValid(HomeRule rule) {
        boolean valid = true;
        
        for(Simulation simulation : Main.getSimulations()) {
            for(RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
                if(rule.equals(ruleProcessPair.getRule())) {
                    valid = false;
                    Utility.getUtility().showWarningDialog("Unable to delete", "Unable to delete \"" +
                            rule.getName() + "\" ", "Simulation \"" + simulation.getName() + "\" contains rule" +
                            " \"" + rule.getName() + "\". Remove it from simulation to enable deletion.");
                }
            }
        }
        return valid;
    }
    
    @FXML
    void switchToCreateNewRule(ActionEvent event) {
        Utility.getUtility().switchPane("/fxml/pages/new_rule.fxml");
    }

    @FXML
    private void back() {
        Utility.getUtility().switchPane("/fxml/pages/main.fxml");
    }

}
