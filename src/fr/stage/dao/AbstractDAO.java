/**
 * 
 */
package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.domainClasses.Page;
import fr.stage.service.FactoryDAO;
import fr.stage.utils.Introspection;

/**
 * @author rnonnon
 * 
 */
public abstract class AbstractDAO<T> implements ICRUDManager<T> {

    protected IConnectionManager connectionManager = ConnectionManager
	    .getInstance();

    protected ResultSet res;

    protected PreparedStatement stm;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Connection beforeOperation() {
	Connection connection = FactoryDAO.getConnectionManagerInstance()
		.getConnection();
	return connection;
    }

    protected void afterOperation(Connection connection) {
	FactoryDAO.getConnectionManagerInstance().closeConnection(connection);
	Introspection.closeSafe(res);
	Introspection.closeSafe(stm);
    }

    public int count(String nameFilter) {
	logger.debug("Start count");
	Connection connection = beforeOperation();
	int total = 0;
	try {
	    total = countBody(nameFilter, connection);
	}
	catch (SQLException e) {

	}
	afterOperation(connection);
	logger.debug("End count");
	return total;
    }

    @Override
    public void create(T object) {
	logger.debug("Start create {}", object);
	Connection connection = beforeOperation();
	try {
	    createBody(object, connection);
	    LogDAO.logInfo("INSERT " + object.toString());
	}
	catch (SQLException e) {
	    LogDAO.logError("INSERT " + object.toString());
	}
	afterOperation(connection);
	logger.debug("End Create {}", object);
    }

    @Override
    public List<T> find(Page page) {
	logger.debug("Start find");
	Connection connection = beforeOperation();
	List<T> res = null;
	try {
	    res = findBody(page, connection);
	}
	catch (SQLException e) {

	}
	afterOperation(connection);
	logger.debug("End find");
	return res;
    }

    @Override
    public void update(final T object) {
	logger.debug("Start update {}", object);
	Connection connection = beforeOperation();
	try {
	    updateBody(object, connection);
	    LogDAO.logInfo("UPDATE  " + object.toString());
	}
	catch (SQLException e) {
	    LogDAO.logError("UPDATE " + object.toString());
	}
	afterOperation(connection);
	logger.debug("End update {}", object);
    }

    @Override
    public void delete(Long id) {
	logger.debug("Start delete {}", id);
	Connection connection = beforeOperation();
	try {
	    deleteBody(id, connection);
	    LogDAO.logInfo("DELETE " + id);
	}
	catch (SQLException e) {
	    LogDAO.logError("DELETE " + id);
	}
	afterOperation(connection);
	logger.debug("End delete {}", id);
    }

    protected abstract void createBody(T object, Connection connection)
	    throws SQLException;

    protected abstract List<T> findBody(Page page, Connection connection)
	    throws SQLException;

    protected abstract void updateBody(T object, Connection connection)
	    throws SQLException;

    protected abstract void deleteBody(Long id, Connection connection)
	    throws SQLException;

    protected abstract int countBody(String nameFilter, Connection connection)
	    throws SQLException;

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
