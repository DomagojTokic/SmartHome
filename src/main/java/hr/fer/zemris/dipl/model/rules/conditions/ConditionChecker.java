package hr.fer.zemris.dipl.model.rules.conditions;

/**
 * Created by Domagoj on 28.4.2017..
 */
public class ConditionChecker {
	
	/**
	 * Checks if given comparison between two values is valid.
	 * @param condition comparator
	 * @param stateValue current homeState value
	 * @param value value to compare to.
	 * @return true if comparison is valid, else false.
	 */
	public static boolean checkNumeric(NumericCondition condition, double stateValue, double value) {
		switch (condition) {
			case LESS:
				if(stateValue < value) {
					return true;
				} else {
					return false;
				}
			case LESS_OR_EQUAL:
				if(stateValue <= value) {
					return true;
				} else {
					return false;
				}
			case EQUAL:
				if(stateValue == value) {
					return true;
				} else {
					return false;
				}
			case GREATER_OR_EQUAL:
				if(stateValue >= value) {
					return true;
				} else {
					return false;
				}
			case GREATER:
				if(stateValue > value) {
					return true;
				} else {
					return false;
				}
		}
		return false;
	}
	
	/**
	 * Checks if {@link BooleanCondition} value corresponds to given homeState.
	 * @param condition BooleanCondition enum
	 * @param stateValue boolean homeState value
	 * @return true if values correspond, else false.
	 */
	public static boolean checkBoolean(BooleanCondition condition, boolean stateValue) {
		if(condition.equals(BooleanCondition.TRUE)) {
			if(stateValue) {
				return true;
			} else {
				return false;
			}
		} else {
			if(!stateValue) {
				return true;
			} else {
				return false;
			}
		}
	}
	
}
