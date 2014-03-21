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

/*
 * TO Do : In DAOComputer :     Traiter le cas d'une compagnie NULL
 * 				Voir pour faire de la cascade
 */
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
	comp.setId(1);// APPLE

	computer.setName("TOTO_COMPUTER4");
	computer.setIntroducedDate(new Date());
	computer.setDiscontinuedDate(new Date());
	computer.setCompany(comp);

	ComputerDao cpDao = ComputerDao.getInstance();
	try {
	    cpDao.create(computer);
	    System.out.println(computer.getId());
	    Computer compFind = new Computer();
	    compFind.setId(1);
	    cpDao.find(compFind);
	    System.out.println(compFind);
	    Computer computerFind2 = cpDao.find(2);
	    System.out.println(computerFind2);
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
