package by.training.karpilovich.lowcost.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateParser {
	
	private DateParser() {}

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public static Calendar parse(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(format.parse(date));
		return calendar;
	}
	
	public static String format(Calendar date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return format.format(new Date(date.getTimeInMillis()));
	}
}