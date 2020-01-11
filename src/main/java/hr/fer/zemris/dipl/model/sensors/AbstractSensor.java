package hr.fer.zemris.dipl.model.sensors;

import hr.fer.zemris.dipl.Main;
import hr.fer.zemris.dipl.gui.observers.IObserver;
import hr.fer.zemris.dipl.model.HomeState;
import hr.fer.zemris.dipl.model.interfaces.Subject;
import hr.fer.zemris.dipl.model.rules.enums.RuleEnum;
import hr.fer.zemris.dipl.model.sensors.virtual.*;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Sensor simulation which changes the value in constant period.
 */
public abstract class AbstractSensor extends Service<Void> implements Subject, Serializable {
	
	protected StringProperty name;
	
	protected String measurement;
	
	protected SensorEnum sensorEnum;
	
	protected RuleEnum ruleEnum;
	
	protected HomeState homeState;
	
	private boolean stop;
	
	protected Random random = new Random();
	
	private List<IObserver> observers = new ArrayList<>();
	
	protected abstract void nextValue() throws InterruptedException;
	
	public SensorEnum getEnum() {
		return sensorEnum;
	}
	
	public RuleEnum getRuleEnum() {
		return ruleEnum;
	}
	
	public void disable() {
		stop = true;
	}
	
	public void enable() {
		stop = false;
	}
	
	public Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while(!stop) {
					nextValue();
					notifyObservers();
					try {
						Thread.sleep(Main.SENSOR_PERIOD * 1000);
					} catch (InterruptedException e) {
						//standard exception when thread is cancelled during sleep
					}
				}
				return null;
			}
		};
	}
	
	@Override
	public void start() {
		enable();
		super.start();
	}
	
	@Override
	public boolean cancel() {
		disable();
		return super.cancel();
	}
	
	private void notifyObservers() {
		for(IObserver observer : observers) {
			observer.update();
		}
	}
	
	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}
	
	@Override
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getMeasurement() {
		return measurement;
	}
	
	public void setHomeState(HomeState homeState) {
		this.homeState = homeState;
	}
	
	public static AbstractSensor createSensor(SensorEnum sensorEnum, HomeState homeState) {
		switch (sensorEnum)
		{
			case CARBON_DIOXIDE :
				return new CarbonDioxideSensor(homeState);
			case TEMPERATURE:
				return new TemperatureSensor(homeState);
			case SMOKE:
				return new SmokeSensor(homeState);
			case MOTION:
				return new MotionSensor(homeState);
			case HUMIDITY:
				return new HumiditySensor(homeState);
			default:
				throw new RuntimeException("Unexpected enum for " + sensorEnum.toString());
		}
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSensor)) return false;
        AbstractSensor that = (AbstractSensor) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
