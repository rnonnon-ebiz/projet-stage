import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.excilys.dao.ConnectionManager;
import fr.excilys.dao.IConnectionManager;
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

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Computer computer = new Computer();
	Field[] fields = computer.getClass().getDeclaredFields();
	System.out.println(fields.length);
	for (int i = 0; i < fields.length; ++i) {
	    System.out.println(i + " : " + fields[i].getType());
	}
    }
}
