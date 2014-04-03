package fr.stage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.stage.dao.CompanyDAO;
import fr.stage.dao.ConnectionManager;
import fr.stage.domain.Company;
import fr.stage.exception.DAOException;

@Service
public class CompanyService {

    @Autowired
    ConnectionManager connectionManager;

    @Autowired
    CompanyDAO companyDAO;

    public boolean exist(long id) {
	boolean companyExistence = false;
	try {
	    companyExistence = companyDAO.exist(id);
	}
	catch (DAOException e) {
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	return companyExistence;
    }

    public Company find(long id) {
	Company res = null;
	try {
	    res = companyDAO.find(id);
	}
	catch (DAOException e) {
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	return res;
    }

    public List<Company> findAll() {
	List<Company> res = null;
	try {
	    res = companyDAO.findAll();
	}
	catch (DAOException e) {
	    throw e;
	}
	finally {
	    connectionManager.closeConnection();
	}
	return res;
    }
}
