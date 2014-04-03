package fr.stage.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static java.sql.Date convertDateToSql(Date date) {
	if (date != null)
	    return new java.sql.Date(date.getTime());
	else
	    return null;
    }

    public static String convertDateToSQLString(String before, Date date, String after, String alternative) {
	StringBuilder result = new StringBuilder();
	result.append(before);
	if (date != null) {
	    result.append(" FROM_UNIXTIME(");
	    result.append(date.getTime() / 1000);
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
	Calendar cal = Calendar.getInstance();
	int year = Integer.parseInt(dateSplitted[0]);
	int month = Integer.parseInt(dateSplitted[1]);
	int day = Integer.parseInt(dateSplitted[2]);
	cal.set(year, month, day);
	return cal.getTimeInMillis();
    }

    public static Date stringToDate(String stringDate) throws NumberFormatException {
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Date d = null;
	try {
	    d = format.parse(stringDate);
	}
	catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return d;
    }

    public static String DateToString(Date date) {
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	String d;
	format.setLenient(false);
	d = format.format(date);
	return d;
    }
}
