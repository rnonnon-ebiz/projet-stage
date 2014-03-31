/**
 * 
 */
package fr.stage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import fr.stage.utils.ConfigFileManipulation;
import fr.stage.utils.Introspection;

/**
 * @author rnonnon
 * 
 */
public class ConnectionManager implements IConnectionManager {

    protected final static Logger logger = LoggerFactory
	    .getLogger(ConnectionManager.class);

    public static final String CONFIG_FILE_NAME = "/fr/stage/dao/dao.properties";

    public static String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private static BoneCP connectionPool;

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>() {

	@Override
	protected Connection initialValue() {
	    Connection connection = null;
	    try {
		connection = connectionPool.getConnection();
		// logger.info("Connection Ready");
		// logger.info("connectionPool free : "
		// + connectionPool.getTotalFree());
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	    return connection;
	}
    };

    private final static ConnectionManager connectionManager = new ConnectionManager();

    private ConnectionManager() {
	// properties = new Properties();
	// properties.put("user", "root");
	// properties.put("password", "mysqlpassword");
	// properties
	// .put("url",
	// "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
	// properties = ConfigFileManipulation
	// .readConfFileAndFill(CONFIG_FILE_NAME);
	// logger.info(properties.toString());
	loadDriver();
	initConnectionPool();
    }

    private void loadDriver() {
	logger.info("Loading driver");
	try {
	    Class.forName(DRIVER_NAME);
	    // create connection
	    // this.connection = (Connection) DriverManager.getConnection(
	    // this.properties.getProperty("url"), properties);
	}
	catch (ClassNotFoundException e) {
	    logger.info("Driver loading error");
	    e.printStackTrace();
	}
	logger.info("Driver loaded");
    }

    private void initConnectionPool() {
	logger.info("Init ConnectionPool");
	BoneCPConfig config = new BoneCPConfig();
	Properties properties = ConfigFileManipulation
		.readConfFileAndFill(CONFIG_FILE_NAME);
	try {
	    config.setJdbcUrl(properties.getProperty("url"));
	    config.setUsername(properties.getProperty("user"));
	    config.setPassword(properties.getProperty("password"));
	    connectionPool = new BoneCP(config);
	    logger.info(connectionPool.getConfig().getJdbcUrl());
	    logger.info("ConnectionPool Ready");
	}
	catch (Exception e) {
	    logger.error("Error on connection pool initialisation");
	    e.printStackTrace();
	}
    }

    public static ConnectionManager getInstance() {
	return connectionManager;
    }

    @Override
    public Connection getConnection() {
	return threadLocal.get();
    }

    @Override
    public void closeConnection() {
	Introspection.closeSafe(threadLocal.get());
	threadLocal.remove();
    }

    public void shutdownPool() {
	connectionPool.shutdown();
    }
}
