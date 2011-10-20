import java.text.ParseException;
import java.text.SimpleDateFormat;

import lib.DateHelper;

import org.junit.Test;

import play.test.UnitTest;

public class DateHelperTest extends UnitTest {
	
	@Test
	public void parseTest() throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		
		assertEquals("simple date", 
				DateHelper.parse("09-05-2011 20:30:42"), 
				format.parseObject("2011-05-09 20:30:42:000")
		);

		assertEquals("simple date, no time", 
				DateHelper.parse("09-05-2011"), 
				format.parseObject("2011-05-09 00:00:00:000")
		);

		assertEquals("simple date, no time, two digit year", 
				DateHelper.parse("09-05-11"), 
				format.parseObject("2011-05-09 00:00:00:000")
		);		

		assertEquals("simple date, two digit year, no seconds", 
				DateHelper.parse("09-05-11"), 
				format.parseObject("2011-05-09 00:00:00:000")
		);		

		assertEquals("simple date, two digit year, no seconds", 
				DateHelper.parse("09-05-11 20:30"), 
				format.parseObject("2011-05-09 20:30:00:000")
		);		

		assertEquals("simple date, two digit year, no minutes", 
				DateHelper.parse("09-05-11 20"), 
				format.parseObject("2011-05-09 20:00:00:000")
		);

		assertEquals("simple date, two digit year, after 1945", 
				DateHelper.parse("01-01-46"), 
				format.parseObject("1946-01-01 00:00:00:000")
		);

		assertEquals("simple date, two digit year, right 45 - 1945", 
				DateHelper.parse("01-01-45"), 
				format.parseObject("1945-01-01 00:00:00:000")
		);

		assertEquals("simple date, two digit year, right before 45 - 2044", 
				DateHelper.parse("31-12-44"), 
				format.parseObject("2044-12-31 00:00:00:000")
		);

		
		assertEquals("simple date, two digit year, before 45", 
				DateHelper.parse("01-01-44"), 
				format.parseObject("2044-01-01 00:00:00:000")
		);
		
	}
	
}
