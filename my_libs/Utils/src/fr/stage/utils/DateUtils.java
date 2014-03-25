package fr.stage.utils;

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
	StringBuilder result = new StringBuilder();
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
}
