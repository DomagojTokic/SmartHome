package hr.fer.zemris.dipl.model;

import hr.fer.zemris.dipl.Factory;
import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.model.sensors.AbstractSensor;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineScheduler;
import org.easyrules.quartz.RulesEngineSchedulerException;

import java.util.Date;
import java.util.List;

/**
 * Created by Domagoj on 27.5.2017..
 */
public class SimulationRunner {
	
	private Simulation simulation;
	
	private HomeState homeState;
	
	private List<AbstractSensor> sensors;
	
	private RulesEngine rulesEngine;
	
	private RulesEngineScheduler scheduler;
	
	private boolean paused = true;
	
	public SimulationRunner(Simulation simulation) {
		this.simulation = simulation;
		homeState = new HomeState();
		this.sensors = Factory.createSensors(simulation, homeState);
		rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();
		try {
			scheduler = RulesEngineScheduler.getInstance();
		} catch (RulesEngineSchedulerException e) {
			e.printStackTrace();
		}
		
		for (RuleProcessPair ruleProcessPair : simulation.getRuleProcessPairs()) {
			ruleProcessPair.setHomeState(homeState);
			ruleProcessPair.getRule().setProcess(ruleProcessPair.getProcess());
			rulesEngine.registerRule(ruleProcessPair.getRule());
		}
	}
	
	public void run() throws InterruptedException {
		for (AbstractSensor sensor : sensors) {
			sensor.reset();
			sensor.start();
		}
		startRuleEngine();
		paused = false;
	}
	
	public void pause() throws RulesEngineSchedulerException {
		for (AbstractSensor sensor : sensors) {
			sensor.cancel();
		}
		scheduler.unschedule(rulesEngine);
		paused = true;
	}
	
	public void startRuleEngine() {
		try {
			scheduler.scheduleAtWithInterval(rulesEngine, new Date(), Main.SENSOR_PERIOD);
			Thread.sleep(100);
			scheduler.start();
		} catch (RulesEngineSchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public List<AbstractSensor> getSensors() {
		return sensors;
	}
	
	public Simulation getSimulation() {
		return simulation;
	}
}
