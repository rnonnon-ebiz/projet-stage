package fr.stage.service;

import fr.stage.dao.CompanyDAO;
import fr.stage.dao.ComputerDAO;

public class FactoryDAO {

    public static ComputerDAO getComputerDAOInstance() {
	return ComputerDAO.getInstance();
    }

    public static CompanyDAO getCompanyDAOInstance() {
	return CompanyDAO.getInstance();
    }
}
