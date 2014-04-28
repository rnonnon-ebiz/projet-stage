package fr.stage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.stage.domain.Log;
import fr.stage.exception.DAOException;
import fr.stage.repository.LogRepo;
import fr.stage.service.LogService;
import fr.stage.util.LogType;

@Service
public class LogServiceImpl implements LogService{

    @Autowired
    LogRepo logRepo;

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
	logRepo.save(new Log(getCallerClassName(), type.toString(), message));
    }
}