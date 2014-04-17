package fr.stage.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

    public static DateTime stringToDate(String stringDate, String pattern)
	    throws NumberFormatException {
	DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
	DateTime d = format.parseDateTime(stringDate);
	return d;
    }

    public static String DateToString(DateTime date, String pattern) {
	DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
	String d = date.toString(format);
	return d;
    }
}
