package fr.excilys.utils;

import java.util.Date;

public class DateUtils {

    public static java.sql.Date convertDateToSql(Date date) {
	return new java.sql.Date(date.getTime());
    }
}
