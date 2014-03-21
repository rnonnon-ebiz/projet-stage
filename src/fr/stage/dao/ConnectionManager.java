/**
 * 
 */
package fr.stage.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

import fr.stage.utils.ConfigFileManipulation;
import fr.stage.utils.Introspection;

/**
 * @author rnonnon
 * 
 */
public class ConnectionManager implements IConnectionManager {

    public static final String CONFIG_FILE_NAME = "conf/jdbcConfigFile.cfg";

    public static String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private Properties properties;

    private Connection connection;

    private final static IConnectionManager connectionManager = new ConnectionManager();

    private ConnectionManager() {
	properties = ConfigFileManipulation
		.readConfFileAndFill(CONFIG_FILE_NAME);
	init();
    }

    private void init() {
	try {
	    Class.forName(DRIVER_NAME);
	    // create connection
	    this.connection = (Connection) DriverManager.getConnection(
		    this.properties.getProperty("url"), properties);
	}
	catch (SQLException | ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static IConnectionManager getInstance() {
	return connectionManager;
    }

    @Override
    public Connection getConnection() {
	try {
	    if (connection.isClosed()) {
		this.connection = (Connection) DriverManager.getConnection(
			this.properties.getProperty("url"), properties);
	    }
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return connection;
    }

    @Override
    public void closeConnection() {
	Introspection.closeSafe(connection);
    }

}
