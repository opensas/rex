package lib;

public class Utils {
	
	public static Boolean isOneOf(String value, String values) {
		
		String[] aValues = values.split(",");

		for(int c=0; c<aValues.length; c++) {
			if (value.equals(aValues[c])) return true;
		}
		
		return false;
	}
	
}
