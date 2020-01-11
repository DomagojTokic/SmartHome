package hr.fer.zemris.dipl;

import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.model.*;
import hr.fer.zemris.dipl.serialization.SerializationRunner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.easyrules.quartz.RulesEngineSchedulerException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Entrance into SmartHomeGUI application.
 */
public class Main extends Application {
	
	/** Static root to pass to the controller */
	private static BorderPane root = new BorderPane();
	
	/** Simulations */
	private static ObservableList<Simulation> simulations = new ObservableListWrapper<>(new ArrayList<>());
	
	/** Rules */
	private static ObservableList<HomeRule> rules = new ObservableListWrapper<>(new ArrayList<>());
	
	/** Processes */
	private static ObservableList<HomeProcess> processes = new ObservableListWrapper<>(new ArrayList<>());
	
	/** Simulation runner */
	private static SimulationRunner simulationRunner;
	
	/** Seconds between every sensor reading and rule engine firing */
	public static final int SENSOR_PERIOD = 1;
	
	/** Instance of {@link hr.fer.zemris.dipl.serialization.SerializationRunner} which is used to store application
	 * state.
	 */
	private static SerializationRunner serializationRunner = new SerializationRunner();
	
	public static void main(String[] args) throws CloneNotSupportedException {
		getApplicationState();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		URL menuBarUrl = getClass().getResource("/fxml/pages/menu.fxml");
		URL paneOneUrl = getClass().getResource("/fxml/pages/main.fxml");
		AnchorPane paneOne = FXMLLoader.load(paneOneUrl);
		MenuBar bar = FXMLLoader.load(menuBarUrl);
		
		// constructing our scene using the static root
		root.setTop(bar);
		root.setCenter(paneOne);
		
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("SmartHome");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> {
			if(simulationRunner != null) {
				try {
					simulationRunner.pause();
				} catch (RulesEngineSchedulerException e) {
					e.printStackTrace();
				}
			}
			saveApplicationState();
			
			Platform.exit();
			System.exit(0);
		});
		primaryStage.show();
	}
	
	/**
	 * Retrieves stored application state.
	 */
	private static void getApplicationState() {
		try {
			simulations = new ObservableListWrapper<>(serializationRunner.deserializeSimulations());
			rules = new ObservableListWrapper<>(serializationRunner.deserializeRules());
			processes = new ObservableListWrapper<>(serializationRunner.deserializeProcesses());
		} catch (IOException e) {
			System.out.println("Some or all files missing from previous execution.");
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves application state
	 */
	private void saveApplicationState() {
		try {
			serializationRunner.serializeSimulations(simulations);
			serializationRunner.serializeRules(rules);
			serializationRunner.serializeProcesses(processes);
		} catch (IOException e) {
			System.out.println("Unable to write current application state!");
		}
	}
	
	public static BorderPane getRoot() {
		return root;
	}
	
	public static ObservableList<Simulation> getSimulations() {
		return simulations;
	}
	
	public static ObservableList<HomeRule> getRules() {
		return rules;
	}
	
	public static ObservableList<HomeProcess> getProcesses() {
		return processes;
	}
	
	public static SimulationRunner getSimulationRunner() {
		return simulationRunner;
	}
	
	public static void setSimulationRunner(SimulationRunner simRunner) {
		simulationRunner = simRunner;
	}
}

