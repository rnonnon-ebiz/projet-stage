/**
 * 
 */
package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.domainClasses.Page;
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
	Connection connection = connectionManager.getConnection();
	return connection;
    }

    protected void afterOperation() {
	connectionManager.closeConnection();
	Introspection.closeSafe(res);
	Introspection.closeSafe(stm);
    }

    public int count(String nameFilter) {
	logger.info("Start count");
	Connection connection = beforeOperation();
	int total = countBody(nameFilter, connection);
	afterOperation();
	logger.info("End count");
	return total;
    }

    @Override
    public void create(T object) {
	logger.info("Start create {}", object);
	Connection connection = beforeOperation();
	createBody(object, connection);
	afterOperation();
	logger.info("End Create {}", object);
    }

    @Override
    public List<T> find(Page page) {
	logger.info("Start find");
	Connection connection = beforeOperation();
	List<T> res = findBody(page, connection);
	afterOperation();
	logger.info("End find");
	return res;
    }

    @Override
    public void update(final T object) {
	logger.info("Start update {}", object);
	Connection connection = beforeOperation();
	updateBody(object, connection);
	afterOperation();
	logger.info("End update {}", object);
    }

    @Override
    public void delete(Long id) {
	logger.info("Start delete {}", id);
	Connection connection = beforeOperation();
	deleteBody(id, connection);
	afterOperation();
	logger.info("End delete {}", id);
    }

    protected abstract void createBody(T object, Connection connection);

    protected abstract List<T> findBody(Page page, Connection connection);

    protected abstract void updateBody(T object, Connection connection);

    protected abstract void deleteBody(Long id, Connection connection);

    protected abstract int countBody(String nameFilter, Connection connection);

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
