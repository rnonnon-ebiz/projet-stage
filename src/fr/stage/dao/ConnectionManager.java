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

import fr.stage.utils.Introspection;

/**
 * @author rnonnon
 * 
 */
public class ConnectionManager implements IConnectionManager {

    public static final String CONFIG_FILE_NAME = "/WEB-INF/conf/jdbcConfigFile.cfg";

    public static String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private Properties properties;

    private BoneCP connectionPool;

    private final static ConnectionManager connectionManager = new ConnectionManager();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConnectionManager() {
	properties = new Properties();
	// properties.put("user", "root");
	// properties.put("password", "mysqlpassword");
	// properties
	// .put("url",
	// "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
	// properties = ConfigFileManipulation
	// .readConfFileAndFill(CONFIG_FILE_NAME);
	logger.info(properties.toString());
	loadDriver();
	initConnectionPool();
    }

    private void loadDriver() {
	logger.info("Loading driver");
	logger.debug("Loading driver");
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
	try {
	    logger.info("Init ConnectionPool");
	    BoneCPConfig config = new BoneCPConfig();
	    config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
	    config.setUsername("root");
	    config.setPassword("mysqlpassword");
	    connectionPool = new BoneCP(config);
	    logger.info("ConnectionPool Ready");

	}
	catch (SQLException e) {
	    logger.error("Error on connection pool initialisation");
	    e.printStackTrace();
	}
    }

    public static ConnectionManager getInstance() {
	return connectionManager;
    }

    @Override
    public Connection getConnection() {
	Connection connection = null;
	try {
	    connection = connectionPool.getConnection();
	    logger.info("Connection Ready");
	    logger.info("connectionPool free : "
		    + connectionPool.getTotalFree());
	    // if (connection.isClosed()) {
	    // this.connection = (Connection) DriverManager.getConnection(
	    // this.properties.getProperty("url"), properties);
	    // }
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return connection;
    }

    @Override
    @Deprecated
    public void closeConnection() {
    }

    public void closeConnection(Connection connection) {
	Introspection.closeSafe(connection);
    }

    public void shutdownPool() {
	connectionPool.shutdown();
    }
}
