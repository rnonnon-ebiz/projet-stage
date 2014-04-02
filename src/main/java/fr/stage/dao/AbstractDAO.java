/**
 * 
 */
package fr.stage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.stage.domainClasses.Page;

/**
 * @author rnonnon
 * 
 */
public abstract class AbstractDAO<T> implements ICRUDManager<T> {

    @Autowired
    protected ConnectionManager connectionManager;

    @Autowired
    protected LogDAO logDao;

    // protected IConnectionManager connectionManager = ConnectionManager
    // .getInstance();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public int count(String nameFilter) {
	logger.debug("Start count");
	int total = 0;
	try {
	    total = countBody(nameFilter);
	}
	catch (SQLException e) {
	}
	finally {
	    connectionManager.closeConnection();
	}
	logger.debug("End count");
	return total;
    }

    @Override
    public void create(T object) {
	logger.debug("Start create {}", object);
	Connection connection = connectionManager.getConnection();
	try {
	    connectionManager.startTransaction();
	    createBody(object);
	    logDao.logInfo("INSERT " + object.toString());
	    connectionManager.endTransaction();
	}
	catch (SQLException e) {
	    rollbackAndLogError("INSERT " + object.toString(), connection);
	}
	finally {
	    connectionManager.closeConnection();
	}
	logger.debug("End Create {}", object);
    }

    @Override
    public List<T> find(Page page) {
	logger.debug("Start find");
	List<T> res = null;
	try {
	    res = findBody(page);
	}
	catch (SQLException e) {

	}
	finally {
	    connectionManager.closeConnection();
	}
	logger.debug("End find");
	return res;
    }

    @Override
    public void update(final T object) {
	logger.debug("Start update {}", object);
	Connection connection = connectionManager.getConnection();
	try {
	    connectionManager.startTransaction();
	    updateBody(object);
	    logDao.logInfo("UPDATE  " + object.toString());
	    connectionManager.endTransaction();
	}
	catch (SQLException e) {
	    rollbackAndLogError("UPDATE " + object.toString(), connection);
	}
	finally {
	    connectionManager.closeConnection();
	}
	logger.debug("End update {}", object);
    }

    @Override
    public void delete(Long id) {
	logger.info("Start delete {}", id);
	Connection connection = connectionManager.getConnection();
	try {
	    connectionManager.startTransaction();
	    deleteBody(id);
	    logDao.logInfo("DELETE " + id);
	    connectionManager.endTransaction();
	}
	catch (SQLException e) {
	    rollbackAndLogError("DELETE " + id, connection);
	}
	finally {
	    connectionManager.closeConnection();
	}
	logger.info("End delete {}", id);
    }

    protected abstract void createBody(T object) throws SQLException;

    protected abstract List<T> findBody(Page page) throws SQLException;

    protected abstract void updateBody(T object) throws SQLException;

    protected abstract void deleteBody(Long id) throws SQLException;

    protected abstract int countBody(String nameFilter) throws SQLException;

    public void rollbackAndLogError(String msg, Connection connection) {
	try {
	    connection.rollback();
	    logDao.logError(msg);
	}
	catch (SQLException e1) {
	    e1.printStackTrace();
	}
    }

    public <idType> String genericFindQuery(String className, idType id) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT * FROM ");
	query.append(className);
	query.append(" WHERE id = ");
	query.append(id);
	return query.toString();
    }

    // public String genericInsertQuery(final T object){
    // StringBuilder query = new StringBuilder();
    // query.append("INSERT INTO ");
    // query.append(object.getClass().getName());
    // query.append(" ( ");
    // final Field[] fields = object.getClass().getDeclaredFields();
    // for(int i=0;i<fields.length;++i){
    // query.append()
    //
    // }
    // query.append(" ) ");
    // return "";
    // }
}
