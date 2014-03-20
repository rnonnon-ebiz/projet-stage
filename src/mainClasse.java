import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import fr.excilys.dao.ComputerDao;
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
	comp.setId(10);

	computer.setName("TOTO_COMPUTER2222");
	computer.setIntroducedDate(new Date());
	computer.setDiscontinuedDate(new Date());
	computer.setCompany(comp);

	ComputerDao cpDao = ComputerDao.getInstance();
	try {
	    cpDao.create(computer);
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
