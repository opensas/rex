package lib;

import java.util.LinkedHashMap;
import java.util.Map;

public class StringHelper {

	public static String toUpperFirst(String value) {
		if (value.length()==0) return "";
		return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
	}
	
	public static String pluralize(String value) {
		
		// http://www.educa.madrid.org/web/ies.complutense.alcala/lengua/Actividades/morfolog/media/EL_SUSTANTIVO/sustantivo_09.htm
		if (value.endsWith("a") ||
			value.endsWith("e") ||
			value.endsWith("é") || 
			value.endsWith("o")) return value + "s";

		if (value.endsWith("s") ||
			value.endsWith("x")) return value;

		if (value.endsWith("z")) {
			return value.substring(0, value.length()-2) + "ces";
		}
		
		return value + "es";
	}
	
	public static Map<String, Object> toMap(String value) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		value = escapeMapText(value);
		
		String[] pairs = value.split(",");
		String[] elements;
		String elementName;
		String elementValue;
		
		for(String pair : pairs) {
			elements = pair.split(":");
			
			elementName = ""; elementValue = "";
			
			if (elements.length>=1) elementName = unescapeMapText(elements[0]);
			if (elements.length>=2) elementValue = unescapeMapText(elements[1]);
			
			if (!"".equals(elementName)) map.put(elementName, elementValue);
		}
		return map;
	}

	public static Map<String, String> toMapString(String value) {
		Map<String, Object> source = toMap(value);
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		for(Map.Entry<String, Object> entry: source.entrySet()) {
			map.put(entry.getKey(), (String) entry.getValue());
		}
		
		return map;
	}
	
	static String escapeMapText(String value) {
		value = value.replaceAll("\\\\:", "___colon___");
		value = value.replaceAll("\\\\,", "___comma___");
		return value;
	}

	static String unescapeMapText(String value) {
		value = value.replaceAll("___colon___", ":" );
		value = value.replaceAll("___comma___", "," );
		return value;
	}

	static String stripFirst(String text, String prefix) {
		if (text.startsWith(prefix)) {
			return text.substring(prefix.length());
		}
		return text;
	}

	static String stripLast(String text, String suffix) {
		if (text.endsWith(suffix)) {
			return text.substring(0,text.length()-suffix.length());
		}
		return text;
	}
	
	static String toFieldName(String title) {
		String fieldName = title;
		
		fieldName = fieldName.replaceAll("á", "a");
		fieldName = fieldName.replaceAll("é", "e");
		fieldName = fieldName.replaceAll("í", "i");
		fieldName = fieldName.replaceAll("ó", "o");
		fieldName = fieldName.replaceAll("ú", "u");

		fieldName = fieldName.replaceAll("Á", "A");
		fieldName = fieldName.replaceAll("É", "E");
		fieldName = fieldName.replaceAll("Í", "I");
		fieldName = fieldName.replaceAll("Ó", "O");
		fieldName = fieldName.replaceAll("Ú", "U");
		
		fieldName = fieldName.replaceAll("ñ", "ni");
		fieldName = fieldName.replaceAll("Ñ", "Ni");
		
		fieldName = fieldName.replaceAll("ü", "u");
		
		fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
		fieldName = fieldName.trim();
		
		return fieldName;
	}
	
}
