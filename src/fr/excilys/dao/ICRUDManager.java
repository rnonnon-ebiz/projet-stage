/**
 * 
 */
package fr.excilys.dao;

import java.sql.SQLException;

/**
 * @author rnonnon
 * 
 */
public interface ICRUDManager<T> {

    public void create(T object) throws SQLException;

    public void find(T object) throws SQLException;

    public void update(T object) throws SQLException;

    public void delete(T object) throws SQLException;

}