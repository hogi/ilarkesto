package ilarkesto.gwt.client;

import java.util.GregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DateTest extends Assert {

	public void addDays() {
		Date date = new Date(2000, 1, 1);

		assertEquals(date.addDays(1), new Date(2000, 1, 2));
		assertEquals(date.addDays(30), new Date(2000, 1, 31));
		assertEquals(date.addDays(31), new Date(2000, 2, 1));
		assertEquals(date.addDays(366), new Date(2001, 1, 1));

		assertEquals(date.addDays(-1), new Date(1999, 12, 31));
		assertEquals(date.addDays(-31), new Date(1999, 11, 30));
	}

	@Test
	public void nextDay() {
		assertEquals(new Date(2009, 10, 26), new Date(2009, 10, 25).nextDay());
		GregorianCalendar cal = new GregorianCalendar();
		for (int i = 0; i < 1600; i++) {
			java.util.Date d1 = cal.getTime();
			cal.add(GregorianCalendar.DAY_OF_MONTH, 1);
			java.util.Date d2 = cal.getTime();
			assertEquals(new Date(d2), new Date(d1).nextDay());
		}
	}

}
