package hr.fer.zemris.dipl.model.interfaces;

/**
 * Created by Domagoj on 14.6.2017..
 */
public abstract class TableViewEditable {
	
	public enum ValueType {
		BOOLEAN,
		DOUBLE
	}
	
	protected ValueType valueType;
	
	public ValueType getValueType() {
		return valueType;
	}
}
