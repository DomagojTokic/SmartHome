package hr.fer.zemris.dipl.model.appliances.enums;

/**
 * Created by Domagoj on 12.6.2017..
 */
public enum ApplianceEnum {
	
	AIR_CONDITIONING ("Air Conditioning"),
	ALARM ("Alarm"),
	CENTRAL_HEATING ("Central Heating"),
	LIGHT ("Lights controller"),
	PHONE ("Phone controller"),
	SHADES ("Shades controller"),
	SOUND_SYSTEM ("Sound System"),
	VENTILATION ("Ventilation"),
	WINDOW ("Window controller");
	
	private final String name;
	
	private ApplianceEnum(String s) {
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
