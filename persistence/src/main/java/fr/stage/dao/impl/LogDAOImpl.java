package fr.stage.dao.impl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.stage.dao.LogDAO;
import fr.stage.exception.DAOException;
import fr.stage.util.LogType;

@Repository
public class LogDAOImpl implements LogDAO {
    @Entity
    @Table(name = "LOGS")
    private class Log{
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long id;

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;

	private String logger;

	private String level;

	private String message;

	public Log(){
	    date = new DateTime();
	}

	public Log(DateTime date, String logger, String level, String message ){
	    this.date = date;
	    this.logger = logger;
	    this.level = level;
	    this.message = message;
	}

	public Log(String logger, String level, String message ){
	    this.date = new DateTime();
	    this.logger = logger;
	    this.level = level;
	    this.message = message;
	}


	public DateTime getDate() {
	    return date;
	}


	public void setDate(DateTime date) {
	    this.date = date;
	}


	public String getLogger() {
	    return logger;
	}


	public void setLogger(String logger) {
	    this.logger = logger;
	}


	public String getLevel() {
	    return level;
	}


	public void setLevel(String level) {
	    this.level = level;
	}


	public String getMessage() {
	    return message;
	}


	public void setMessage(String message) {
	    this.message = message;
	}
    }

    @Autowired
    SessionFactory sessionFactory;

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

    @Override
    public void logError(String message) throws DAOException {
	log(LogType.ERROR, message);
    }

    @Override
    public void logInfo(String message) throws DAOException {
	log(LogType.INFO, message);
    }

    @Override
    public void logWarning(String message) throws DAOException {
	log(LogType.WARN, message);
    }

    @Override
    public void logFatal(String message) throws DAOException {
	log(LogType.FATAL, message);
    }

    private void log(LogType type, String message) throws DAOException {
	sessionFactory.getCurrentSession().persist(new Log(getCallerClassName(), type.toString(), message));
    }
}
