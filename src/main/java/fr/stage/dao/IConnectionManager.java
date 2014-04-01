/**
 * 
 */
package fr.stage.dao;

import java.sql.Connection;

/**
 * @author rnonnon
 * 
 */
public interface IConnectionManager {

    public Connection getConnection();

    public void closeConnection();
}
