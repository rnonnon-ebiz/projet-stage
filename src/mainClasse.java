import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import service.ServiceDAO;
import fr.excilys.dao.CompanyDAO;
import fr.excilys.dao.ComputerDAO;
import fr.excilys.dao.ConnectionManager;
import fr.excilys.dao.IConnectionManager;
import fr.excilys.domainClasses.Company;
import fr.excilys.domainClasses.Computer;

public class mainClasse {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	IConnectionManager coMan = ConnectionManager.getInstance();
	Connection co = coMan.getConnection();
	try {
	    ResultSet res;
	    Statement stm = co.createStatement();
	    res = stm.executeQuery("SELECT * FROM company");
	    while (res.next()) {
		System.out.println(res.getString("name"));
	    }

	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Computer computer = new Computer();
	Company comp = new Company();
	comp.setName("totoCompany");

	computer.setName("TOTO_COMPUTER5");
	// computer.setIntroducedDate(new Date());
	computer.setDiscontinuedDate(new Date());
	// computer.setCompany(comp);

	ComputerDAO computerDAO = ServiceDAO.getComputerDAOInstance();
	CompanyDAO companyDAO = ServiceDAO.getCompanyDAOInstance();
	try {
	    companyDAO.create(comp);
	    computerDAO.create(computer);
	    System.out.println(computer.getId());
	    Computer compFind = new Computer();
	    compFind.setId(1);
	    computerDAO.find(compFind);
	    System.out.println(compFind);
	    Computer computerFind2 = computerDAO.find(2);
	    System.out.println(computerFind2);
	    computerDAO.delete(616);

	    // CompanyDAO.getInstance().delete(company);
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
