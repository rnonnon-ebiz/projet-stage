package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.stage.service.FactoryDAO;
import fr.stage.utils.Introspection;

public class QueryObjects {

    private Connection connection;

    private ResultSet res;

    private PreparedStatement stm;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public QueryObjects() {
	connection = FactoryDAO.getConnectionManagerInstance().getConnection();
	res = null;
	stm = null;

    }

    public void rollbackAndLogError(String msg) {
	try {
	    rollbackTransaction();
	    autoCommit();
	    LogDAO.logError(msg);
	}
	catch (SQLException e1) {
	    e1.printStackTrace();
	}
    }

    public void autoCommit() throws SQLException {
	connection.setAutoCommit(true);
    }

    public void startTransaction() throws SQLException {
	connection.setAutoCommit(false);
    }

    public void endTransaction() throws SQLException {
	connection.commit();
    }

    public void rollbackTransaction() throws SQLException {
	connection.rollback();
    }

    public Connection getConnection() {
	return connection;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
	stm = connection.prepareStatement(query);
	return stm;
    }

    public PreparedStatement prepareStatement(String query, int params)
	    throws SQLException {
	stm = connection.prepareStatement(query, params);
	return stm;
    }

    public ResultSet executeQuery() throws SQLException {
	res = stm.executeQuery();
	return res;
    }

    public ResultSet executeUpdate() throws SQLException {
	stm.executeUpdate();
	return res;
    }

    public ResultSet executeUpdateGeneratedKeys() throws SQLException {
	stm.executeUpdate();
	res = stm.getGeneratedKeys();
	return res;
    }

    public void setConnection(Connection connection) {
	this.connection = connection;
    }

    public ResultSet getRes() {
	return res;
    }

    public void setRes(ResultSet res) {
	this.res = res;
    }

    public PreparedStatement getStm() {
	return stm;
    }

    public void setStm(PreparedStatement stm) {
	this.stm = stm;
    }

    public void afterOperation() {
	FactoryDAO.getConnectionManagerInstance().closeConnection();
	Introspection.closeSafe(res);
	Introspection.closeSafe(stm);
    }
}
