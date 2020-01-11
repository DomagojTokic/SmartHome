package hr.fer.zemris.dipl.serialization;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.javafx.collections.ObservableListWrapper;
import hr.fer.zemris.dipl.model.HomeProcess;
import hr.fer.zemris.dipl.model.HomeRule;
import hr.fer.zemris.dipl.model.RuleProcessPair;
import hr.fer.zemris.dipl.model.Simulation;
import hr.fer.zemris.dipl.model.actions.AbstractAction;
import hr.fer.zemris.dipl.model.actions.concrete.*;
import hr.fer.zemris.dipl.model.appliances.AbstractAppliance;
import hr.fer.zemris.dipl.model.appliances.concrete.*;
import hr.fer.zemris.dipl.model.rules.AbstractRule;
import hr.fer.zemris.dipl.model.rules.concrete.*;
import org.easyrules.api.Rule;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Domagoj on 17.6.2017..
 */
public class SerializationRunner {
	
	private static Gson fxGson;
	
	public SerializationRunner() {
		initializeGson();
	}
	
	private static void initializeGson() {
		RuntimeTypeAdapterFactory<AbstractRule> ruleTypeFactory = RuntimeTypeAdapterFactory
				.of(AbstractRule.class, "type")
				.registerSubtype(AlarmRule.class)
				.registerSubtype(CarbonDioxideRule.class)
				.registerSubtype(HumidityRule.class)
				.registerSubtype(LightRule.class)
				.registerSubtype(MotionRule.class)
				.registerSubtype(SmokeRule.class)
				.registerSubtype(SoundSystemRule.class)
				.registerSubtype(TemperatureRule.class);
		
		RuntimeTypeAdapterFactory<AbstractAction> actionTypeFactory = RuntimeTypeAdapterFactory
				.of(AbstractAction.class, "type")
				.registerSubtype(AlarmAction.class)
				.registerSubtype(CarbonDioxideAction.class)
				.registerSubtype(HeatingAction.class)
				.registerSubtype(HumidityAction.class)
				.registerSubtype(LightAction.class)
				.registerSubtype(PhoneAction.class)
				.registerSubtype(ShadesAction.class)
				.registerSubtype(SoundSystemAction.class)
				.registerSubtype(TemperatureAction.class);
		
		RuntimeTypeAdapterFactory<AbstractAppliance> applianceTypeFactory = RuntimeTypeAdapterFactory
				.of(AbstractAppliance.class, "type")
				.registerSubtype(AirConditioning.class)
				.registerSubtype(Alarm.class)
				.registerSubtype(CentralHeating.class)
				.registerSubtype(Lights.class)
				.registerSubtype(Phone.class)
				.registerSubtype(Shades.class)
				.registerSubtype(SoundSystem.class)
				.registerSubtype(Ventilation.class)
				.registerSubtype(Window.class);
		
		fxGson = FxGson.coreBuilder()
				.registerTypeAdapterFactory(ruleTypeFactory)
				.registerTypeAdapterFactory(actionTypeFactory)
				.registerTypeAdapterFactory(applianceTypeFactory)
				.setExclusionStrategies(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes fieldAttributes) {
						return false;
					}
					
					@Override
					public boolean shouldSkipClass(Class<?> aClass) {
						return aClass == Rule.class;
					}
				})
				.create();
	}
	
	public void serializeSimulations(List<Simulation> simulations) throws IOException {
		FileOutputStream fileOut = new FileOutputStream("simulations.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(fxGson.toJson(simulations.toArray()));
		out.close();
		fileOut.close();
		System.out.printf("Serialized data is saved in simulations.ser\n");
	}
	
	public void serializeRules(List<HomeRule> rules) throws IOException {
		FileOutputStream fileOut = new FileOutputStream("rules.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(fxGson.toJson(rules.toArray()));
		out.close();
		fileOut.close();
		System.out.printf("Serialized data is saved in rules.ser\n");
	}
	
	public void serializeProcesses(List<HomeProcess> processes) throws IOException {
		FileOutputStream fileOut = new FileOutputStream("processes.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(fxGson.toJson(processes.toArray()));
		out.close();
		fileOut.close();
		System.out.printf("Serialized data is saved in processes.ser\n");
	}
	
	public List<Simulation> deserializeSimulations() throws IOException, CloneNotSupportedException, ClassNotFoundException {
		Type listType = new TypeToken<ArrayList<Simulation>>() {}.getType();
		FileInputStream fileIn = new FileInputStream("simulations.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		List<Simulation> simulations = fxGson.fromJson((String) in.readObject(), listType);
		for (Simulation simulation : simulations) {
			for (RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
				ruleProcessPair.setRule((HomeRule) ruleProcessPair.getRule().clone());
			}
		}
		in.close();
		fileIn.close();
		
		return simulations;
	}
	
	public List<HomeProcess> deserializeProcesses() throws IOException, ClassNotFoundException {
		Type listType = new TypeToken<ArrayList<HomeProcess>>() {}.getType();
		FileInputStream fileIn = new FileInputStream("processes.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		List<HomeProcess> processes = new ObservableListWrapper<>(fxGson.fromJson((String) in.readObject(), listType));
		in.close();
		fileIn.close();
		
		return processes;
	}
	
	public List<HomeRule> deserializeRules() throws IOException, CloneNotSupportedException, ClassNotFoundException {
		Type listType = new TypeToken<ArrayList<HomeRule>>() {}.getType();
		FileInputStream fileIn = new FileInputStream("rules.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		List<HomeRule> storedRules = fxGson.fromJson((String) in.readObject(), listType);
		List<HomeRule> rules = new ArrayList<>();
		for (HomeRule rule : storedRules) {
			rules.add((HomeRule) rule.clone());
		}
		in.close();
		fileIn.close();
		
		return rules;
	}
	
}
