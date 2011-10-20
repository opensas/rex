import org.junit.Test;

import play.test.UnitTest;

import static lib.Utils.isOneOf;

public class Utils_isOneOfTest extends UnitTest {

	@Test
	public void isOneOfTest() {
		
		assertTrue("empty string against empty string", 
				isOneOf("", ""));

		assertFalse("value against empty string", 
				isOneOf("value", ""));

		assertFalse("empty string against value", 
				isOneOf("", "value1"));

		assertTrue("value against single value list", 
				isOneOf("value1", "value1"));

		assertTrue("first value against value list", 
				isOneOf("value1", "value1,value2,value3"));
		
		assertTrue("last value against value list", 
				isOneOf("value3", "value1,value2,value3"));

		assertFalse("case sensitive value against value list", 
				isOneOf("value3", "Value1,Value2,Value3"));

		assertFalse("taking spaces into account", 
				isOneOf("  value3  ", " Value1 , Value2 , Value3 "));

		
	}
	
}
