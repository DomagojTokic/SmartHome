package hr.fer.zemris.dipl.model;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

/**
 * Created by Domagoj on 28.4.2017..
 */
public class HomeState implements Serializable {
	
	/*******************************************************************/
	/**                        Sensor readings                         */
	/*******************************************************************/
	
	/** Percentage of carbon dioxide in air */
	private Double carbonDioxide = 600.0;
	
	/** Water concentration in air */
	private Double humidity = 30.0;
	
	/** Is motion detected in simulated room */
	private boolean motionDetected;
	
	/** Is smoke detected in simulated room */
	private boolean smokeDetected;
	
	/** Room temperature */
	private Double temperature = 20.0;
	
	/*******************************************************************/
	/**                      Humidity parameters                       */
	/*******************************************************************/
	
	/** Minimum humidity value */
	private Double minHumidity = 0.0;
	
	/** Maximum humidity value */
	private Double maxHumidity = 100.0;
	
	/** Humidity step value */
	private Double stepHumidity = 0.5;
	
	/** Humidity outside simulation. Room value is always getting close to this value. */
	private volatile Double humidityWeight = 50.0;
	
	/** When action ends, this is the the value to which humidityWeight goes to */
	private static final Double DEFAULT_HUMIDITY_WEIGHT = 50.0;
	
	/** Minimum humidity change per second */
	private Double minHumidityChange = 0.02;
	
	/** Maximum humidity change per second */
	private Double maxHumidityChange = 0.05;
	
	/** Humidity change multiplier */
	private volatile Double humidityMultiplier = 1.0;
	
	/** Humidity change multiplier increment */
	private volatile Double humidityMultiplierInc = 0.0;
	
	/** Only one action can change humidity change parameters at time */
	Semaphore humiditySemaphore = new Semaphore(1);
	
	/** Signifies the end of humidity action */
	Semaphore humidityEndSemaphore = new Semaphore(0);
	
	/*******************************************************************/
	/**                    Temperature parameters                      */
	/*******************************************************************/
	
	/** Minimum temperature value */
	private Double minTemperature = -30.0;
	
	/** Maximum temperature value */
	private Double maxTemperature = 200.0;
	
	/** Temperature step value */
	private Double stepTemperature = 0.5;
	
	/** Outside temperature. Room value is always getting close to this value. */
	private volatile Double temperatureWeight = 25.0;
	
	/** When action ends, this is the the value to which temperatureWeight goes to */
	private static final Double DEFAULT_TEMPERATURE_WEIGHT = 25.0;
	
	/** Minimum temperature change per second */
	private Double minTemperatureChange = 0.01;
	
	/** Maximum temperature change per second */
	private Double maxTemperatureChange = 0.02;
	
	/** Temperature change multiplier */
	private volatile Double temperatureMultiplier = 1.0;
	
	/** Temperature change multiplier increment */
	private volatile Double temperatureMultiplierInc = 0.0;
	
	/** Only one action can change temperature change parameters at time */
	Semaphore temperatureSemaphore = new Semaphore(1);
	
	/** Signifies the end of temperature action */
	Semaphore temperatureEndSemaphore = new Semaphore(0);
	
	/*******************************************************************/
	/**                  Carbon Dioxide parameters                     */
	/*******************************************************************/
	
	/** Minimum Carbon Dioxide value */
	private Double minCarbonDioxide = 250.0;
	
	/** Maximum Carbon Dioxide value */
	private Double maxCarbonDioxide = 40000.0;
	
	/** Carbon Dioxide step value */
	private Double stepCarbonDioxide = 5.0;
	
	/**
	 * Carbon Dioxide weight value. Room value is always getting close to this value. It's considered that Carbon
	 * dioxide value will never surpass this value because of natural air flow - e.g. open doors.
	 */
	private volatile Double carbonDioxideWeight = 2000.0;
	
	/** When action ends, this is the the value to which carbonDioxideWeight goes to */
	private static final Double DEFAULT_CARBON_DIOXIDE_WEIGHT = 2000.0;
	
	/** Minimum Carbon Dioxide change per second */
	private volatile Double minCarbonDioxideChange = 1.0;
	
	/** Maximum Carbon Dioxide change per second */
	private Double maxCarbonDioxideChange = 3.0;
	
	/** Carbon Dioxide change multiplier */
	private volatile Double carbonDioxideChangeMultiplier = 1.0;
	
	/** Carbon Dioxide change multiplier increment */
	private Double carbonDioxideChangeMultiplierInc = 0.0;
	
	/** Only one action can change carbon dioxide change parameters at time */
	Semaphore carbonDioxideSemaphore = new Semaphore(1);
	
	/** Signifies the end of carbon dioxide action */
	Semaphore carbonDioxideEndSemaphore = new Semaphore(0);
	
	/*******************************************************************/
	/**                  Boolean homeState parameters                      */
	/*******************************************************************/
	
	/** Motion detected homeState change percentage */
	private Double motionChangePercentage = 0.1;
	
	/** Smoke detected homeState change percentage */
	private Double smokeChangePercentage = 0.01;
	
	/*******************************************************************/
	/**              Appliance-related homeState parameters                */
	/*******************************************************************/
	
	private volatile boolean light;
	
	private volatile boolean soundSystemState;
	
	private volatile boolean shades;
	
	private volatile boolean phone;
	
	private volatile boolean alarm;
	
	/*******************************************************************/
	
	public Double getCarbonDioxide() {
		return carbonDioxide;
	}
	
	public void setCarbonDioxide(Double carbonDioxide) {
		this.carbonDioxide = carbonDioxide;
	}
	
	public Double getHumidity() {
		return humidity;
	}
	
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	
	public Boolean getMotionDetected() {
		return motionDetected;
	}
	
	public void setMotionDetected(Boolean motionDetected) {
		this.motionDetected = motionDetected;
	}
	
	public Boolean getSmokeDetected() {
		return smokeDetected;
	}
	
	public void setSmokeDetected(Boolean smokeDetected) {
		this.smokeDetected = smokeDetected;
	}
	
	public Double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	
	public Double getHumidityWeight() {
		return humidityWeight;
	}
	
	public void setHumidityWeight(Double humidityWeight) {
		this.humidityWeight = humidityWeight;
	}
	
	public Double getMinHumidityChange() {
		return minHumidityChange;
	}
	
	public void setMinHumidityChange(Double minHumidityChange) {
		this.minHumidityChange = minHumidityChange;
	}
	
	public Double getMaxHumidityChange() {
		return maxHumidityChange;
	}
	
	public void setMaxHumidityChange(Double maxHumidityChange) {
		this.maxHumidityChange = maxHumidityChange;
	}
	
	public Double getHumidityMultiplier() {
		return humidityMultiplier;
	}
	
	public void setHumidityMultiplier(Double humidityChangeMultiplier) {
		this.humidityMultiplier = humidityChangeMultiplier;
	}
	
	public Double getHumidityMultiplierInc() {
		return humidityMultiplierInc;
	}
	
	public void setHumidityMultiplierInc(Double humidityChangeMultiplierInc) {
		this.humidityMultiplierInc = humidityChangeMultiplierInc;
	}
	
	public Double getTemperatureWeight() {
		return temperatureWeight;
	}
	
	public void setTemperatureWeight(Double temperatureWeight) {
		this.temperatureWeight = temperatureWeight;
	}
	
	public Double getMinTemperatureChange() {
		return minTemperatureChange;
	}
	
	public void setMinTemperatureChange(Double minTemperatureChange) {
		this.minTemperatureChange = minTemperatureChange;
	}
	
	public Double getMaxTemperatureChange() {
		return maxTemperatureChange;
	}
	
	public void setMaxTemperatureChange(Double maxTemperatureChange) {
		this.maxTemperatureChange = maxTemperatureChange;
	}
	
	public Double getTemperatureMultiplier() {
		return temperatureMultiplier;
	}
	
	public void setTemperatureMultiplier(Double temperatureChangeMultiplier) {
		this.temperatureMultiplier = temperatureChangeMultiplier;
	}
	
	public Double getTemperatureMultiplierInc() {
		return temperatureMultiplierInc;
	}
	
	public void setTemperatureMultiplierInc(Double temperatureChangeMultiplierInc) {
		this.temperatureMultiplierInc = temperatureChangeMultiplierInc;
	}
	
	public Double getCarbonDioxideWeight() {
		return carbonDioxideWeight;
	}
	
	public void setCarbonDioxideWeight(Double carbonDioxideWeight) {
		this.carbonDioxideWeight = carbonDioxideWeight;
	}
	
	public Double getMinCarbonDioxideChange() {
		return minCarbonDioxideChange;
	}
	
	public void setMinCarbonDioxideChange(Double minCarbonDioxideChange) {
		this.minCarbonDioxideChange = minCarbonDioxideChange;
	}
	
	public Double getMaxCarbonDioxideChange() {
		return maxCarbonDioxideChange;
	}
	
	public void setMaxCarbonDioxideChange(Double maxCarbonDioxideChange) {
		this.maxCarbonDioxideChange = maxCarbonDioxideChange;
	}
	
	public Double getCarbonDioxideChangeMultiplier() {
		return carbonDioxideChangeMultiplier;
	}
	
	public void setCarbonDioxideMultiplier(Double carbonDioxideChangeMultiplier) {
		this.carbonDioxideChangeMultiplier = carbonDioxideChangeMultiplier;
	}
	
	public Double getCarbonDioxideMultiplierInc() {
		return carbonDioxideChangeMultiplierInc;
	}
	
	public void setCarbonDioxideMultiplierInc(Double carbonDioxideChangeMultiplierInc) {
		this.carbonDioxideChangeMultiplierInc = carbonDioxideChangeMultiplierInc;
	}
	
	public Double getMotionChangePercentage() {
		return motionChangePercentage;
	}
	
	public void setMotionChangePercentage(Double motionChangePercentage) {
		this.motionChangePercentage = motionChangePercentage;
	}
	
	public Double getSmokeChangePercentage() {
		return smokeChangePercentage;
	}
	
	public void setSmokeChangePercentage(Double smokeChangePercentage) {
		this.smokeChangePercentage = smokeChangePercentage;
	}
	
	public Boolean getLight() {
		return light;
	}
	
	public void setLight(Boolean light) {
		this.light = light;
	}
	
	public Boolean getSoundSystemState() {
		return soundSystemState;
	}
	
	public void setSoundSystemState(Boolean soundSystemState) {
		this.soundSystemState = soundSystemState;
	}
	
	public Boolean getShades() {
		return shades;
	}
	
	public void setShades(Boolean shades) {
		this.shades = shades;
	}
	
	public Boolean getPhone() {
		return phone;
	}
	
	public void setPhone(Boolean phone) {
		this.phone = phone;
	}
	
	public Boolean getAlarm() {
		return alarm;
	}
	
	public void setAlarm(Boolean alarm) {
		this.alarm = alarm;
	}
	
	public Double getMinHumidity() {
		return minHumidity;
	}
	
	public Double getMaxHumidity() {
		return maxHumidity;
	}
	
	public Double getMinTemperature() {
		return minTemperature;
	}
	
	public Double getMaxTemperature() {
		return maxTemperature;
	}
	
	public Double getMinCarbonDioxide() {
		return minCarbonDioxide;
	}
	
	public Double getMaxCarbonDioxide() {
		return maxCarbonDioxide;
	}
	
	public Semaphore getHumiditySemaphore() {
		return humiditySemaphore;
	}
	
	public Semaphore getTemperatureSemaphore() {
		return temperatureSemaphore;
	}
	
	public Semaphore getCarbonDioxideSemaphore() {
		return carbonDioxideSemaphore;
	}
	
	public Semaphore getHumidityEndSemaphore() {
		return humidityEndSemaphore;
	}
	
	public Semaphore getTemperatureEndSemaphore() {
		return temperatureEndSemaphore;
	}
	
	public Semaphore getCarbonDioxideEndSemaphore() {
		return carbonDioxideEndSemaphore;
	}
	
	public Double getStepHumidity() {
		return stepHumidity;
	}
	
	public Double getStepTemperature() {
		return stepTemperature;
	}
	
	public Double getStepCarbonDioxide() {
		return stepCarbonDioxide;
	}
	
	public void resetTemperatureChangeParameters() {
		temperatureWeight = DEFAULT_TEMPERATURE_WEIGHT;
		temperatureMultiplier = 1.0;
		temperatureMultiplierInc = 0.0;
	}
	
	public void resetHumidityChangeParameters() {
		humidityWeight = DEFAULT_HUMIDITY_WEIGHT;
		humidityMultiplier = 1.0;
		humidityMultiplierInc = 0.0;
	}
	
	public void resetCarbonDioxideChangeParameters() {
		carbonDioxideWeight = DEFAULT_CARBON_DIOXIDE_WEIGHT;
		humidityMultiplier = 1.0;
		humidityMultiplierInc = 0.0;
	}
}
