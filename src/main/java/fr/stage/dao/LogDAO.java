package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;

import fr.stage.exception.DAOException;
import fr.stage.util.ConnectionUtil;
import fr.stage.util.LogType;

@Repository
public class LogDAO {

    @Autowired
    private BoneCPDataSource dataSource;

    public static String logQuery = "INSERT INTO LOGS (date, logger, level, message) VALUES (FROM_UNIXTIME(?), ?, ?, ?)";

    public LogDAO() {
    }

    public String getCallerClassName() {
	String name;
	// Get the Stack
	StackTraceElement[] stack = new Throwable().getStackTrace();
	StackTraceElement ste = null;
	// Find caller class
	if (stack.length > 1) {
	    ste = stack[1];
	    // Get her name
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

    private void log(LogType type, String message) throws DAOException {
	Connection connection = DataSourceUtils.getConnection(dataSource);
	// Get the caller class
	String caller = getCallerClassName();
	PreparedStatement stm = null;
	try {
	    // Prepare the statement
	    stm = connection.prepareStatement(logQuery);
	    // Fill it
	    stm.setLong(1, (new DateTime().getMillis()) / 1000L);
	    stm.setString(2, caller);
	    stm.setString(3, type.toString());
	    stm.setString(4, message);
	    // Execute it
	    stm.executeUpdate();
	}
	catch (SQLException e) {
	    throw new DAOException("Failed to log");
	}
	finally {
	    ConnectionUtil.close(stm);
	}
    }
}
