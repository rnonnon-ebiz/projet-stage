package fr.stage.dao.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.stage.dao.LogDAO;
import fr.stage.exception.DAOException;
import fr.stage.util.LogType;

@Repository
public class LogDAOImpl implements LogDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String logQuery = "INSERT INTO LOGS (date, logger, level, message) VALUES (?, ?, ?, ?)";

    public LogDAOImpl() {
    }

    private String getCallerClassName() {
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

    public void logError(String message) throws DAOException {
	log(LogType.ERROR, message);
    }

    public void logInfo(String message) throws DAOException {
	log(LogType.INFO, message);
    }

    public void logWarning(String message) throws DAOException {
	log(LogType.WARN, message);
    }

    public void logFatal(String message) throws DAOException {
	log(LogType.FATAL, message);
    }

    private void log(LogType type, String message) throws DAOException {
	// Generate args
	Object[] args = generateInsertArgs(type, message);
	try {
	    jdbcTemplate.update(logQuery, args);
	}
	catch (DataAccessException e) {
	    throw new DAOException("Failed to log");
	}
    }

    private Object[] generateInsertArgs(LogType type, String message) {
	Object[] args = new Object[4];
	// Set Date
	args[0] = new DateTime().toDate();
	// Set Caller class
	String caller = getCallerClassName();
	args[1] = caller;
	// Set Error Type
	args[2] = type.toString();
	// Set Message
	args[3] = message;
	return args;
    }
}
