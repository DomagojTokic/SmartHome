package hr.fer.zemris.dipl;

import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.HomeProcess;
import hr.fer.zemris.dipl.model.HomeRule;
import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.appliances.*;
import hr.fer.zemris.dipl.model.appliances.concrete.*;
import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.rules.*;
import hr.fer.zemris.dipl.model.rules.concrete.*;
import hr.fer.zemris.dipl.model.sensors.*;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Domagoj on 22.4.2017..
 */
public class Factory {
	
	private static Factory factory;
	
	private Factory() {
	}
	
	public static Factory getFactory() {
		if (factory == null) {
			factory = new Factory();
		}
		return factory;
	}
	
	public Simulation createSimulation(String name, List<SensorEnum> sensors,
	                             List<ApplianceEnum> applianceEnums) {
		Simulation simulation = new Simulation(name);
		simulation.setApplianceEnums(applianceEnums);
		simulation.setSensors(sensors);
		
		return simulation;
	}
	
	public void createRule(String name, String description, List<AbstractRule> conditions) {
		HomeRule rule = new HomeRule(name, description);
		
		for (AbstractRule r : conditions) {
			rule.addRule(r);
		}
		
		Main.getRules().add(rule);
	}
	
	public void createProcess(String name, String description, List<AbstractAction> actions) {
		HomeProcess process = new HomeProcess(name, description, actions);
		Main.getProcesses().add(process);
	}
	
	public static Map<String, AbstractAppliance> createAppliances() {
		Map<String, AbstractAppliance> appliances = new HashMap<>();
		
		AbstractAppliance appliance = new AirConditioning();
		appliances.put(appliance.getName(), appliance);
		
		appliance = new Alarm();
		appliances.put(appliance.getName(), appliance);
		
		appliance = new CentralHeating();
		appliances.put(appliance.getName(), appliance);
		
		appliance = new Lights();
		appliances.put(appliance.getName(), appliance);
		
		appliance = new Phone();
		appliances.put(appliance.getName(), appliance);
		
		appliance = new Shades();
		appliances.put(appliance.getName(), appliance);
		
		appliance = new Ventilation();
		appliances.put(appliance.getName(), appliance);
		
		appliance = new Window();
		appliances.put(appliance.getName(), appliance);
		
		return appliances;
	}
	
	public Map<String, AbstractRule> createRules() {
		Map<String, AbstractRule> rules = new HashMap<>();
		
		AbstractRule rule = new CarbonDioxideRule();
		rules.put(rule.getName(), rule);
		
		rule = new HumidityRule();
		rules.put(rule.getName(), rule);
		
		rule = new MotionRule();
		rules.put(rule.getName(), rule);
		
		rule = new SmokeRule();
		rules.put(rule.getName(), rule);
		
		rule = new TemperatureRule();
		rules.put(rule.getName(), rule);
		
		rule = new LightRule();
		rules.put(rule.getName(), rule);
		
		rule = new SoundSystemRule();
		rules.put(rule.getName(), rule);
		
		rule = new AlarmRule();
		rules.put(rule.getName(), rule);
		
		return rules;
	}
	
	public static List<AbstractSensor> createSensors(Simulation simulation, HomeState homeState) {
		List<AbstractSensor> sensors = new ArrayList<>();
		
		for(SensorEnum sensorEnum : simulation.getSensorEnums()) {
			AbstractSensor sensor = AbstractSensor.createSensor(sensorEnum, homeState);
			sensors.add(sensor);
		}
		
		return sensors;
	}
}
