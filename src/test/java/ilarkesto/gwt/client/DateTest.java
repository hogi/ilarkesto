package ilarkesto.gwt.client;

import java.util.GregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DateTest extends Assert {

	@Test
	public void addDays() {
		Date date = new Date(2010, 1, 1);

		for (int i = -10000; i < 10000; i++) {
			assertAddDays(date, i);
		}
	}

	private void assertAddDays(Date begin, int days) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(begin.toMillis());
		assertEquals(begin, new Date(calendar.getTime()));

		calendar.add(GregorianCalendar.DAY_OF_YEAR, days);

		assertEquals(begin.addDays(days), new Date(calendar.getTime()));
	}

}
