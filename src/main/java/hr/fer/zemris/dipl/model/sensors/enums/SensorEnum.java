package hr.fer.zemris.dipl.model.sensors.enums;

/**
 * Created by Domagoj on 12.6.2017..
 */
public enum SensorEnum {
	
	CARBON_DIOXIDE ("CO2 sensor"),
	HUMIDITY ("Humidity sensor"),
	TEMPERATURE ("Temperature sensor"),
	SMOKE ("Smoke sensor"),
	MOTION ("Motion sensor");
	
	private final String name;
	
	private SensorEnum(String s) {
		name = s;
	}
	
	public boolean equalsName(String otherName) {
		// (otherName == null) check is not needed because name.equals(null) returns false
		return name.equals(otherName);
	}
	
	public String toString() {
		return this.name;
	}
}
