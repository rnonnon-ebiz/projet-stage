package service;

import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ComputerDAO;

public class ServiceDAO {

    public static ComputerDAO getComputerDAOInstance() {
	return ComputerDAO.getInstance();
    }

    public static CompanyDAO getCompanyDAOInstance() {
	return CompanyDAO.getInstance();
    }
}
