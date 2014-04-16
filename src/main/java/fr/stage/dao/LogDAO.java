package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import fr.stage.util.LogType;

@Repository
public class LogDAO {

    private DataSource dataSource;

    public static String logQuery = "INSERT INTO LOGS (date, logger, level, message) VALUES (FROM_UNIXTIME(?), ?, ?, ?)";

    public LogDAO() {
    }

    public String getCallerClassName() {
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

    public void logError(String message) {
	log(LogType.ERROR, message);
    }

    public void logInfo(String message) {
	log(LogType.INFO, message);
    }

    public void logWarning(String message) {
	log(LogType.WARN, message);
    }

    public void logFatal(String message) {
	log(LogType.FATAL, message);
    }

    private void log(LogType type, String message) {
	Connection connection;
	try {
	    connection = dataSource.getConnection();

	    String caller = getCallerClassName();
	    PreparedStatement stm = null;
	    try {
		stm = connection.prepareStatement(logQuery);
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
	    finally {
		connection.close();
	    }
	}
	catch (SQLException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }
}
