package fr.stage.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static java.sql.Date convertDateToSql(Date date) {
	if (date != null)
	    return new java.sql.Date(date.getTime());
	else
	    return null;
    }

    public static String convertDateToSQLString(String before, Date date,
	    String after, String alternative) {
	StringBuffer result = new StringBuffer();
	result.append(before);
	if (date != null) {
	    result.append(" FROM_UNIXTIME(");
	    result.append(date.getTime());
	    result.append(") ");
	}
	else {
	    result.append(alternative);
	}
	result.append(after);
	return result.toString();
    }

    public static long stringToTimestamp(String stringDate)
	    throws NumberFormatException {
	String[] dateSplitted = stringDate.split("-");
	Calendar cal = Calendar.getInstance();
	int year = Integer.parseInt(dateSplitted[0]);
	int month = Integer.parseInt(dateSplitted[1]);
	int day = Integer.parseInt(dateSplitted[2]);
	cal.set(year, month, day);
	return cal.getTimeInMillis();
    }

    public static Date stringToDate(String stringDate)
	    throws NumberFormatException {
	String[] dateSplitted = stringDate.split("-");
	Calendar cal = Calendar.getInstance();
	for (int i = 0; i < dateSplitted.length; ++i)
	    System.out.println(i + " ; " + dateSplitted[i]);
	int year = Integer.parseInt(dateSplitted[0]);
	int month = Integer.parseInt(dateSplitted[1]);
	int day = Integer.parseInt(dateSplitted[2]);
	cal.set(year, month, day);
	return cal.getTime();
    }
}
