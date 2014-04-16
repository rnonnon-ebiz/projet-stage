/**
 * 
 */
package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;

import fr.stage.exception.DAOException;

/**
 * @author rnonnon
 * 
 */
@Repository
public class ConnectionManager {

    protected Logger logger;

    @Autowired
    @Qualifier(value = "BoneCP")
    private BoneCPDataSource dataSource;

    private ThreadLocal<Connection> threadLocal;

    public ConnectionManager() {
	logger = LoggerFactory.getLogger(ConnectionManager.class);
	initThreadLocal();
    }

    public void initThreadLocal() {
	threadLocal = new ThreadLocal<Connection>() {

	    @Override
	    protected Connection initialValue() {
		Connection connection = null;
		try {
		    connection = dataSource.getConnection();
		}
		catch (SQLException e) {
		    e.printStackTrace();
		}
		return connection;
	    }
	};
    }

    public Connection getConnection() {
	return threadLocal.get();
    }

    public void closeConnection() {
	try {
	    threadLocal.get().close();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
	threadLocal.remove();
    }

    public void startTransaction() {
	try {
	    threadLocal.get().setAutoCommit(false);
	}
	catch (SQLException e) {
	    throw new DAOException("Transaction Start Exception");
	}
    }

    public void endTransaction() {
	try {
	    threadLocal.get().commit();
	}
	catch (SQLException e) {
	    throw new DAOException("Transaction End Exception");
	}

    }

    public void rollbackAndStopTransaction() throws SQLException {
	threadLocal.get().rollback();
	threadLocal.get().setAutoCommit(true);
    }

    public static void close(ResultSet res) {
	if (res != null) {
	    try {
		res.close();
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void close(PreparedStatement stm) {
	if (stm != null) {
	    try {
		stm.close();
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void close(Statement stm) {
	if (stm != null) {
	    try {
		stm.close();
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void close(ResultSet res, Statement stm) {
	close(res);
	close(stm);
    }

    public static void close(ResultSet res, PreparedStatement stm) {
	close(res);
	close(stm);
    }
}
