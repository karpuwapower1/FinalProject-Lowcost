package by.training.karpilovich.lowcost.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateParser {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public Calendar getDateFromString(String date) throws ParseException {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(FORMAT.parse(date));
		return calendar;
	}

}
