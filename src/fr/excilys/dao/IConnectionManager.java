/**
 * 
 */
package fr.excilys.dao;

import com.mysql.jdbc.Connection;

/**
 * @author rnonnon
 * 
 */
public interface IConnectionManager {

    public Connection getConnection();

    public void closeConnection();
}
