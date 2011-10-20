import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.DataHelper;
import lib.DataHelper.Condition;
import lib.DataHelper.FieldType;
import lib.DataHelper.Operator;

import org.junit.Test;

import play.test.UnitTest;

public class DataHelper_parseConditionTest extends UnitTest {
    
	@Test
	public void parseConditionTest() {

		assertEquals("empty condition :", 
				new Condition(), 
				DataHelper.parseCondition("field", FieldType.UNKNOWN, ""));
		
		assertEquals("negated condition, deafult operator, string type: !value1", 
				new Condition(Operator.STARTS_WITH, "not (field like ?)", asList("value1%"), FieldType.STRING, true),
				DataHelper.parseCondition("field", FieldType.STRING, "!value1"));

		assertEquals("negated condition, default operator, integer type: !22", 
				new Condition(Operator.EQUAL, "not (field = ?)", asList(22), FieldType.INTEGER, true).hashCode(),
				DataHelper.parseCondition("field", FieldType.INTEGER, "!22").hashCode());

		assertEquals("negated condition, default operator, integer type: !22 ", 
				new Condition(Operator.EQUAL, "not (field = ?)", asList(22), FieldType.INTEGER, true),
				DataHelper.parseCondition("field", FieldType.INTEGER, "!22"));

		assertEquals("negated condition, default operator, integer type: !22 ", 
				new Condition(Operator.EQUAL, "not (field = ?)", asList(22L), FieldType.LONG, true),
				DataHelper.parseCondition("field", FieldType.LONG, "!22"));

		assertEquals("negated condition, default operator, multiple values, integer type: !22;34;45 ", 
				new Condition(Operator.IN, "not (field in (?, ?, ?))", asList(22, 34, 45), FieldType.INTEGER, true),
				DataHelper.parseCondition("field", FieldType.INTEGER, "!22;34;45"));

		assertEquals("not equal condition, string type: <>value1", 
				new Condition(Operator.EQUAL, "not (field = ?)", asList("value1"), FieldType.STRING, true), 
				DataHelper.parseCondition("field", FieldType.STRING, "<>value1"));

		assertEquals("lesser than or equal, string type: <=value1", 
				new Condition(Operator.LESS_OR_EQUAL, "field <= ?", asList("value1"), FieldType.STRING, false), 
				DataHelper.parseCondition("field", FieldType.STRING, "<=value1"));

		assertEquals("greater than or equal, string type: >=value1", 
				new Condition(Operator.GREATER_OR_EQUAL, "field >= ?", asList("value1"), FieldType.STRING, false), 
				DataHelper.parseCondition("field", FieldType.STRING, ">=value1"));
		
		assertEquals("contains condition, string type: *value1*", 
				new Condition(Operator.CONTAINS, "field like ?", asList("%value1%"), FieldType.STRING, false), 
				DataHelper.parseCondition("field", FieldType.STRING, "*value1*"));

		assertEquals("greater than, string type: >value1*", 
				new Condition(Operator.GREATER, "field > ?", asList("value1*"), FieldType.STRING, false), 
				DataHelper.parseCondition("field", FieldType.STRING, ">value1*"));

		assertEquals("lesser than, string type: <value1*", 
				new Condition(Operator.LESS, "field < ?", asList("value1*"), FieldType.STRING, false), 
				DataHelper.parseCondition("field", FieldType.STRING, "<value1*"));

		assertEquals("starts with, string type: value1*", 
				new Condition(Operator.STARTS_WITH, "field like ?", asList("value1%"), FieldType.STRING, false), 
				DataHelper.parseCondition("field", FieldType.STRING, "value1*"));

		assertEquals("between, string type: value1..value2", 
				new Condition(Operator.BETWEEN, "field between ? and ?", asList("value1", "value2"), FieldType.STRING, false), 
				DataHelper.parseCondition("field", FieldType.STRING, "value1..value2"));	

		assertEquals("between, string type: 20..30", 
				new Condition(Operator.BETWEEN, "field between ? and ?", asList(20, 30), FieldType.INTEGER, false), 
				DataHelper.parseCondition("field", FieldType.INTEGER, "20..30"));	
		
		assertEquals("negated between, type string: !value1..value2", 
				new Condition(Operator.BETWEEN, "not (field between ? and ?)", asList("value1", "value2"), FieldType.STRING, true), 
				DataHelper.parseCondition("field", FieldType.STRING, "!value1..value2"));

	}
	
	private List<Object> asList(Object... elements) {
		return Arrays.asList(elements);
	}
	
}
