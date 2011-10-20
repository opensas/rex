package lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

	public static Date parse(String value) {
		Date date = null;
		Date digitDate = parseFormat("01-01-1945", "dd-MM-yyyy");
		
		String[] formats = {
				"dd-MM-yy HH:mm:ss", "dd-MM-yy HH:mm", "dd-MM-yy HH", "dd-MM-yy",
				"dd-MM-yyyy HH:mm:ss", "dd-MM-yyyy HH:mm", "dd-MM-yyyy HH", "dd-MM-yyyy"
		};

		for (String format: formats) {
			date = parseFormat(value, format, digitDate);
			if (date!=null) return date;
		}
	
		return date;
	}
	
	private static Date parseFormat(String value, String format) {
		return parseFormat(value, format, null);
	}
	
	private static Date parseFormat(String value, String format, Date digitDate) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		if (digitDate!=null) dateFormat.set2DigitYearStart(digitDate);
		
		try {
			date = dateFormat.parse(value);
		} catch (ParseException e) {}
		return date;
	}
	
}
