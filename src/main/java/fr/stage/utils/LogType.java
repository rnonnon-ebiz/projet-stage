package fr.stage.utils;

public enum LogType {
    ERROR("error"), INFO("info"), WARN("warning"), FATAL("fatal");

    private String value = "";

    LogType(String value) {
	this.value = value;
    }

    public String toString() {
	return value;
    }
}
