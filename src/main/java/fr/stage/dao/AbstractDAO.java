/**
 * 
 */
package fr.stage.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.domainClasses.Page;

/**
 * @author rnonnon
 * 
 */
public abstract class AbstractDAO<T> implements ICRUDManager<T> {

    // @Autowired
    // protected IConnectionManager connectionManager;

    // protected IConnectionManager connectionManager = ConnectionManager
    // .getInstance();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public int count(String nameFilter) {
	logger.debug("Start count");
	QueryObjects qObjects = new QueryObjects();
	int total = 0;
	try {
	    total = countBody(nameFilter, qObjects);
	}
	catch (SQLException e) {
	}
	qObjects.afterOperation();
	logger.debug("End count");
	return total;
    }

    @Override
    public void create(T object) {
	logger.debug("Start create {}", object);
	QueryObjects qObjects = new QueryObjects();
	try {
	    qObjects.startTransaction();
	    createBody(object, qObjects);
	    LogDAO.logInfo("INSERT " + object.toString());
	    qObjects.endTransaction();
	}
	catch (SQLException e) {
	    qObjects.rollbackAndLogError("INSERT " + object.toString());
	}
	qObjects.afterOperation();
	logger.debug("End Create {}", object);
    }

    @Override
    public List<T> find(Page page) {
	logger.debug("Start find");
	QueryObjects qObjects = new QueryObjects();
	List<T> res = null;
	try {
	    res = findBody(page, qObjects);
	}
	catch (SQLException e) {

	}
	qObjects.afterOperation();
	logger.debug("End find");
	return res;
    }

    @Override
    public void update(final T object) {
	logger.debug("Start update {}", object);
	QueryObjects qObjects = new QueryObjects();
	try {
	    qObjects.startTransaction();
	    updateBody(object, qObjects);
	    LogDAO.logInfo("UPDATE  " + object.toString());
	    qObjects.endTransaction();
	}
	catch (SQLException e) {
	    qObjects.rollbackAndLogError("UPDATE " + object.toString());
	}
	qObjects.afterOperation();
	logger.debug("End update {}", object);
    }

    @Override
    public void delete(Long id) {
	logger.debug("Start delete {}", id);
	QueryObjects qObjects = new QueryObjects();
	try {
	    qObjects.startTransaction();
	    deleteBody(id, qObjects);
	    LogDAO.logInfo("DELETE " + id);
	    qObjects.endTransaction();
	}
	catch (SQLException e) {
	    qObjects.rollbackAndLogError("DELETE  " + id);
	}
	qObjects.afterOperation();
	logger.debug("End delete {}", id);
    }

    protected abstract void createBody(T object, QueryObjects qObjects)
	    throws SQLException;

    protected abstract List<T> findBody(Page page, QueryObjects qObjects)
	    throws SQLException;

    protected abstract void updateBody(T object, QueryObjects qObjects)
	    throws SQLException;

    protected abstract void deleteBody(Long id, QueryObjects qObjects)
	    throws SQLException;

    protected abstract int countBody(String nameFilter, QueryObjects qObjects)
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
