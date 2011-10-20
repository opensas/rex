package lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHelper {
	
	public static class FilterCondition {
		public String condition = "";
		public Object[] params = {};
		
		public FilterCondition() {
			
		}
		
		public FilterCondition(String condition, Object[] params) {
			super();
			this.condition = condition;
			this.params = params;
		}
		
		public FilterCondition(String condition, List<Object> params) {
			super();
			this.condition = condition;
			this.params = (Object[]) params.toArray(new Object[params.size()]);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((condition == null) ? 0 : condition.hashCode());
			result = prime * result + Arrays.hashCode(params);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			FilterCondition other = (FilterCondition) obj;
			if (condition == null) {
				if (other.condition != null) return false;
			} else if (!condition.equals(other.condition)) return false;
			if (!Arrays.equals(params, other.params)) return false;
			return true;
		}

		@Override
		public String toString() {
			return "FilterCondition [condition=" + condition + ", params="
					+ Arrays.toString(params) + "]";
		}
		
	}
	
	public enum FieldType {
		STRING, INTEGER, LONG, BOOLEAN, DATE, UNKNOWN
	}
	
	public static FieldType fieldType(Class clazz, String field) throws Exception {
		String type = clazz.getField(field).getType().getName();
		
		if ("java.lang.String".equals(type)) return FieldType.STRING;
		if ("java.lang.Integer".equals(type)) return FieldType.INTEGER;
		if ("java.lang.Long".equals(type)) return FieldType.LONG;
		if ("java.lang.Boolean".equals(type)) return FieldType.BOOLEAN;
		if ("java.util.Date".equals(type)) return FieldType.DATE;
		
		return FieldType.UNKNOWN;
	}
	
	public static FilterCondition filterCondition(
			Class clazz, 
			Map<String, String> values) throws Exception {
		
		String query = "";
		List<Object> params = new ArrayList<Object>();
		
		String field, condition;
		Condition parsedCondition;
		
		for(Entry<String, String> entry: values.entrySet()) {
			field = entry.getKey();
			condition = entry.getValue();
			
			if (!"".equals(field) && !"".equals(condition)) {
				parsedCondition = parseCondition(field, DataHelper.fieldType(clazz, field), condition);
				query = query + "(" + parsedCondition.query + ") and ";
				params.addAll(parsedCondition.values);
			}
		}
		query = StringHelper.stripLast(query, " and ");
		
		return new FilterCondition(query, params);
	}

	public static Condition parseCondition(String field, FieldType type, String condition) {
		String operator;
		Condition c = new Condition();

		c.type = type;
		if ("".equals(condition)) return c;
		
		//negated --Indica si tiene un not adelante
		if (condition.startsWith("!")) {
			c.negated = true;
			condition = condition.substring(1);
		}

		// <> -- mayor menor <>
		if (condition.startsWith("<>")) {
			c.operator = Operator.EQUAL;
			c.negated = true;
			c.addValue(condition.substring(2));
			c.query = field + " = ?";
		
		// <= o >= -- Mayor o igual (>=), Menor o igual (<=)
		} else if (condition.matches("^(<=|>=).*")) {
			operator = condition.substring(0, 2);
			if ("<=".equals(operator)) c.operator = Operator.LESS_OR_EQUAL;
			if (">=".equals(operator)) c.operator = Operator.GREATER_OR_EQUAL;
			c.addValue(condition.substring(2));
			c.query = field + " " + operator + " ?";

		// * ... * -- Contiene (*...*)
		} else if (condition.matches("^(\\*|\\$).*")) {
			c.operator = Operator.CONTAINS;
			
			// get rid of last "*"
			if (condition.endsWith("*")) condition = condition.substring(0,condition.length()-1);
			
			c.addValue("%" + condition.substring(1) + "%");
			c.query = field + " like ?";

		// <, >, =, $ -- Mayor (>), Menor (<), Igual (=), Contiene ($)
		} else if (condition.matches("^(<|>|=).*")) {
			operator = condition.substring(0,1);
			
			if ("<".equals(operator)) c.operator = Operator.LESS;
			if (">".equals(operator)) c.operator = Operator.GREATER;
			if ("=".equals(operator)) c.operator = Operator.EQUAL;
			if ("$".equals(operator)) c.operator = Operator.CONTAINS;
			if ("*".equals(operator)) c.operator = Operator.CONTAINS;
			
			c.addValue(condition.substring(1));
			c.query = field + " " + operator + " ?";

		// starts with (*) -- Comienza con (*)
		} else if (condition.matches(".*\\*$")) {
			c.operator = Operator.STARTS_WITH;
			c.values.add(condition.substring(0,condition.length()-1) + "%");
			c.query = field + " like ?";
		
		} else if (condition.matches(".+\\.\\..+")) {

			// between -- value1..value2
			Pattern pattern = Pattern.compile("(.+)\\.\\.(.+)");
			Matcher matcher = pattern.matcher(condition);
			if (matcher.matches()) {
				c.operator = Operator.BETWEEN;
				c.addValue(matcher.group(1));
				c.addValue(matcher.group(2));
				c.query = field + " between ? and ?";
			}

		// in -- value1;value2;value3
		} else if (condition.contains(";")) {
			c.operator = Operator.IN;
			c.query = field + " in (";
			String[] values = condition.split(";");
			
			for(String value: values) {
				c.query += "?, ";
				c.addValue(value);
			}
			c.query = StringHelper.stripLast(c.query, ", ") + ")";
		}
		
		// set default operator, and then process again
		if (c.operator==Operator.NONE) {
			// no operator specified, set default operator and process again
			switch (type) {
			case BOOLEAN: case DATE: case INTEGER: case LONG:
				condition = "=" + condition;
				break;
			case STRING:
				condition = condition + "*";
				break;
			}
			if (c.negated==true) condition = "!" + condition;
			return parseCondition(field, type, condition);
		}

		if(c.negated==true) c.query = "not (" + c.query + ")";
		
		return c;
	}

	public static Object castValue(String value, FieldType type) {
		Object o;
		
		switch (type) {
		case STRING:
			o = value;
			break;
		case INTEGER:
			o = Integer.parseInt(value);
			break;
		case LONG:
			o = Long.parseLong(value);
			break;
		case BOOLEAN:
			o = Boolean.parseBoolean(value);
			break;
		case DATE:
			o = DateHelper.parse(value);
			break;
		case UNKNOWN:
		default:
			o = value;
			break;
		}
		return o;
	}
	
	
	@Override
	public String toString() {
		return "DataHelper []";
	}

	public enum Operator {
		EQUAL, GREATER, LESS, GREATER_OR_EQUAL, LESS_OR_EQUAL,
		CONTAINS, STARTS_WITH, BETWEEN, IN, NONE
	}
	
	public static class Condition {
		public Operator operator = Operator.NONE;
		public String query = "";
		public List<Object> values = new ArrayList<Object>();
		public FieldType type = FieldType.UNKNOWN;
		public Boolean negated = false;

		public Condition() {
		}

		public Condition(Operator operator, String query, List<Object> values,
				FieldType type, Boolean negated) {
			super();
			this.operator = operator;
			this.query = query;
			this.values = values;
			this.type = type;
			this.negated = negated;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((negated == null) ? 0 : negated.hashCode());
			result = prime * result
					+ ((operator == null) ? 0 : operator.hashCode());
			result = prime * result + ((query == null) ? 0 : query.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			result = prime * result
					+ ((values == null) ? 0 : values.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Condition other = (Condition) obj;
			if (negated == null) {
				if (other.negated != null) return false;
			} else if (!negated.equals(other.negated)) return false;
			if (operator != other.operator) return false;
			if (query == null) {
				if (other.query != null) return false;
			} else if (!query.equals(other.query)) return false;
			if (type != other.type) return false;
			if (values == null) {
				if (other.values != null) return false;
			} else if (!values.equals(other.values)) return false;
			return true;
		}

		@Override
		public String toString() {
			return "Condition [operator=" + operator + ", query=" + query
					+ ", values=" + values + ", type=" + type + ", negated="
					+ negated + "]";
		}

		public void addValue(Object value) {
			values.add(castValue((String) value, this.type));
		}


	}
	
}
