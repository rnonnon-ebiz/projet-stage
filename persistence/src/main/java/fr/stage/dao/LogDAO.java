package fr.stage.dao;

import fr.stage.exception.DAOException;

public interface LogDAO {

    public void logError(String message) throws DAOException;

    public void logInfo(String message) throws DAOException;

    public void logWarning(String message) throws DAOException;

    public void logFatal(String message) throws DAOException;

}
