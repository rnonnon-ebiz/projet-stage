package fr.stage.service;


public interface LogService {

    public void logError(String message);

    public void logInfo(String message);

    public void logWarning(String message);

    public void logFatal(String message);

}
