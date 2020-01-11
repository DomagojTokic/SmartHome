package hr.fer.zemris.dipl.model.rules.conditions;

/**
 * Created by Domagoj on 27.4.2017..
 */
public enum NumericCondition {
	LESS {
		@Override
		public String toString() {
			return "<";
		}
	},
	LESS_OR_EQUAL {
		@Override
		public String toString() {
			return "<=";
		}
	},
	EQUAL {
		@Override
		public String toString() {
			return "=";
		}
	},
	GREATER_OR_EQUAL {
		@Override
		public String toString() {
			return ">=";
		}
	},
	GREATER  {
		@Override
		public String toString() {
			return ">";
		}
	}
}
