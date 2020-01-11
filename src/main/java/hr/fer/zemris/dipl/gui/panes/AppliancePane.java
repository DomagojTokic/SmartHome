package hr.fer.zemris.dipl.gui.panes;

import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Domagoj on 26.5.2017..
 */
public class AppliancePane {
	
	private AbstractAppliance appliance;
	
	private AnchorPane anchorPane;
	
	private String panePath = "/fxml/templates/appliance.fxml";
	
	private Label applianceLabel;
	
	private TextField stateTextField;
	
	private String applianceNameLabelId = "applianceName";
	private String stateFieldId = "homeState";
	
	public AppliancePane(AbstractAppliance appliance) throws IOException {
		this.appliance = appliance;
		
		URL paneURL = getClass().getResource(panePath);
		anchorPane = FXMLLoader.load(paneURL);
		
		List<Node> parameterFields = anchorPane.getChildren();
		for (Node parameterField : parameterFields) {
			String parameterFieldId = parameterField.getId();
			
			if (parameterFieldId != null) {
				if (parameterFieldId.equals(applianceNameLabelId)) {
					applianceLabel = (Label) parameterField;
					applianceLabel.setText(appliance.getName());
					
				} else if (parameterFieldId.equals(stateFieldId)) {
					stateTextField = (TextField) parameterField;
					if(appliance.getApplianceState()) {
						stateTextField.setText("ON");
					} else {
						stateTextField.setText("OFF");
					}
				}
			}
		}
	}
	
	public AbstractAppliance getAppliance() {
		return appliance;
	}
	
	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
	
	public void setStateTextField(boolean state) {
		if(state) {
			stateTextField.setText("ON");
		} else {
			stateTextField.setText("OFF");
		}
	}
}
