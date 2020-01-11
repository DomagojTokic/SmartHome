package hr.fer.zemris.dipl.model.rules.conditions;

/**
 * Created by Domagoj on 27.4.2017..
 */
public enum BooleanCondition {
	TRUE,
	FALSE;
	
	/**
	 * Converts {@link BooleanCondition} to standard boolean type.
	 * @param condition BooleanCondition enum
	 * @return boolean value of BooleanCondition enum
	 */
	public static boolean getBoolean(BooleanCondition condition) {
		if(condition.equals(BooleanCondition.TRUE)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Converts {@link BooleanCondition} to standard boolean type.
	 * @param condition BooleanCondition enum
	 * @return boolean value of BooleanCondition enum
	 */
	public static BooleanCondition getBooleanCondition(boolean condition) {
		if(condition) {
			return BooleanCondition.TRUE;
		} else {
			return BooleanCondition.FALSE;
		}
	}
}
