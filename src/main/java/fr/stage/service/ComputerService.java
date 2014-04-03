package fr.stage.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.stage.dao.ComputerDAO;
import fr.stage.dao.ConnectionManager;
import fr.stage.dao.LogDAO;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.exception.DAOException;

@Service
public class ComputerService {

    @Autowired
    ConnectionManager connectionManager;

    @Autowired
    LogDAO logDao;

    @Autowired
    ComputerDAO computerDAO;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean exist(long id) {
	boolean computerExistence = false;
	try {
	    computerExistence = computerDAO.exist(id);
	}
	catch (DAOException e) {
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	return computerExistence;
    }

    public int count(String nameFilter) {
	logger.debug("Start count");
	int total = 0;
	try {
	    total = computerDAO.count(nameFilter);
	}
	catch (DAOException e) {
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	logger.debug("End count");
	return total;
    }

    public void create(Computer computer) {
	try {
	    logger.debug("Start Transaction");

	    connectionManager.startTransaction();
	    computerDAO.create(computer);
	    logDao.logInfo("INSERT " + computer.toString());
	    connectionManager.endTransaction();

	    logger.debug("Transaction Ended");
	}
	catch (DAOException e) {
	    rollbackAndLogError("INSERT " + computer.toString());
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
    }

    // Find By ID
    public Computer find(long id) {
	Computer res = null;
	try {
	    res = computerDAO.find(id);
	}
	catch (DAOException e) {
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	return res;
    }

    // Find By Page Parameters
    public List<Computer> find(Page page) {
	List<Computer> res = null;
	try {
	    res = computerDAO.find(page);
	}
	catch (DAOException e) {
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	return res;
    }

    public void update(Computer computer) {
	try {
	    logger.debug("Start Transaction");

	    connectionManager.startTransaction();
	    computerDAO.update(computer);
	    logDao.logInfo("UPDATE  " + computer.toString());
	    connectionManager.endTransaction();

	    logger.debug("Transaction Ended");
	}
	catch (DAOException e) {
	    rollbackAndLogError("UPDATE  " + computer.toString());
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
    }

    public boolean delete(Long id) {
	boolean deleteSuccess = false;
	try {
	    logger.debug("Start Transaction");

	    connectionManager.startTransaction();
	    deleteSuccess = computerDAO.delete(id);
	    logDao.logInfo("DELETE " + id);
	    connectionManager.endTransaction();

	    logger.debug("Transaction Ended");
	}
	catch (DAOException e) {
	    rollbackAndLogError("DELETE " + id);
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	return deleteSuccess;
    }

    private void rollbackAndLogError(String error) {
	try {
	    connectionManager.rollbackAndStopTransaction();
	    logDao.logError(error);
	}
	catch (SQLException e1) {
	    e1.printStackTrace();
	}
    }
}
