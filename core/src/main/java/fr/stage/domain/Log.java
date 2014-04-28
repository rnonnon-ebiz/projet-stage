package fr.stage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "LOGS")
public class Log{
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




