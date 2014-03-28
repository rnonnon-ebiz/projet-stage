package fr.stage.service;

import fr.stage.dao.CompanyDAO;
import fr.stage.dao.ComputerDAO;
import fr.stage.dao.ConnectionManager;

public class FactoryDAO {

    public static ConnectionManager getConnectionManagerInstance() {
	return ConnectionManager.getInstance();
    }

    public static ComputerDAO getComputerDAOInstance() {
	return ComputerDAO.getInstance();
    }

    public static CompanyDAO getCompanyDAOInstance() {
	return CompanyDAO.getInstance();
    }
}
