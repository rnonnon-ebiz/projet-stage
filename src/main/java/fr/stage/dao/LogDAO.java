package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import fr.stage.service.FactoryDAO;
import fr.stage.utils.LogType;

public enum LogDAO {
    INSTANCE;

    public static String logQuery = "INSERT INTO LOGS (date, logger, level, message) VALUES (FROM_UNIXTIME(?), ?, ?, ?)";

    public static String getCallerClassName() {
	String name;
	StackTraceElement[] stack = new Throwable().getStackTrace();
	StackTraceElement ste = null;
	if (stack.length > 1) {
	    ste = stack[1];
	    name = ste.getClassName();
	}
	else {
	    name = "unknown";
	}
	return name;
    }

    public static void logError(String message) {
	log(LogType.ERROR, message);
    }

    public static void logInfo(String message) {
	log(LogType.INFO, message);
    }

    public static void logWarning(String message) {
	log(LogType.WARN, message);
    }

    public static void logFatal(String message) {
	log(LogType.FATAL, message);
    }

    private static void log(LogType type, String message) {
	Connection connection = FactoryDAO.getConnectionManagerInstance()
		.getConnection();
	String caller = getCallerClassName();
	try {
	    PreparedStatement stm = connection.prepareStatement(logQuery);
	    stm.setLong(1, (new Date().getTime()) / 1000L);
	    stm.setString(2, caller);
	    stm.setString(3, type.toString());
	    stm.setString(4, message);
	    stm.executeUpdate();
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
