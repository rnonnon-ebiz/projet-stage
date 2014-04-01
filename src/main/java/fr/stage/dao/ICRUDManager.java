/**
 * 
 */
package fr.stage.dao;

import java.sql.SQLException;
import java.util.List;

import fr.stage.domainClasses.Page;

/**
 * @author rnonnon
 * 
 */
public interface ICRUDManager<T> {

    public void create(T object) throws SQLException;

    public List<T> find(Page page) throws SQLException;

    public void update(T object) throws SQLException;

    public void delete(Long id) throws SQLException;

}