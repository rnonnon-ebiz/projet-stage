package fr.stage.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

    public static String convertDateToSQLString(String before, DateTime date, String after,
	    String alternative) {
	StringBuilder result = new StringBuilder();
	result.append(before);
	if (date != null) {
	    result.append(" FROM_UNIXTIME(");
	    result.append(date.getMillis());
	    result.append(") ");
	}
	else {
	    result.append(alternative);
	}
	result.append(after);
	return result.toString();
    }

    public static long stringToTimestamp(String stringDate) throws NumberFormatException {
	String[] dateSplitted = stringDate.split("-");
	int year = Integer.parseInt(dateSplitted[0]);
	int month = Integer.parseInt(dateSplitted[1]);
	int day = Integer.parseInt(dateSplitted[2]);
	DateTime date = new DateTime(year, month, day, 0, 0);
	return date.getMillis();
    }

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
