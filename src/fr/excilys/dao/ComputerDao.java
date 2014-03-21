package fr.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import fr.excilys.domainClasses.Company;
import fr.excilys.domainClasses.Computer;
import fr.excilys.utils.DateUtils;

public class ComputerDao extends AbstractCRUDManager<Computer> {

    private Connection connection = connectionManager.getConnection();

    private static final ComputerDao computerDao = new ComputerDao();

    private ComputerDao() {
    }

    public static ComputerDao getInstance() {
	return computerDao;
    }

    public Computer find(int id) throws SQLException {
	Computer comp = new Computer();
	comp.setId(id);
	find(comp);
	return comp;
    }

    public void delete(int id) throws SQLException {
	Connection connection = beforeOperation();
	Computer comp = new Computer();
	comp.setId(id);
	deleteBody(comp, connection);
	afterOperation();
    }

    @Override
    protected void createBody(Computer computer, Connection connection) {
	try {
	    String query = generateInsertQuery(computer);
	    System.out.println(query);

	    stm = connection.createStatement();
	    stm.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	    res = stm.getGeneratedKeys();
	    if (res.next()) {
		computer.setId(res.getInt(1));
	    }
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void findBody(Computer computer, Connection connection) {
	try {
	    String query = generateFindQuery(computer);
	    System.out.println(query);

	    stm = connection.createStatement();
	    res = stm.executeQuery(query);
	    if (res.next()) {
		computer.setDiscontinuedDate(res.getDate("discontinued"));
		computer.setIntroducedDate(res.getDate("introduced"));
		computer.setName(res.getString("name"));
		int idCompany = res.getInt("company_id");
		Company company = new Company();
		company.setId(idCompany);
		CompanyDAO.getInstance().find(company);
		computer.setCompany(company);
	    }
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void updateBody(Computer object, Connection connection) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void deleteBody(Computer computer, Connection connection) {
	try {
	    String query = generateDeleteQuery(computer);
	    System.out.println(query);

	    stm = connection.createStatement();
	    stm.executeUpdate(query);
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    private String generateInsertQuery(Computer computer) {
	StringBuffer query = new StringBuffer();
	query.append("INSERT INTO computer ");
	query.append(" ( name , introduced , discontinued , company_id ) VALUES ('");
	query.append(computer.getName());
	query.append("'");
	query.append(DateUtils.convertDateToSQLString(",",
		computer.getIntroducedDate(), "", "NULL"));
	query.append(DateUtils.convertDateToSQLString(",",
		computer.getDiscontinuedDate(), "", "NULL"));
	query.append(" , ");
	query.append(computer.getCompany().getId());
	query.append(" )");
	return query.toString();
    }

    private String generateFindQuery(Computer computer) {
	StringBuffer query = new StringBuffer();
	query.append("SELECT * FROM ");
	query.append("computer cr JOIN company cy");
	query.append(" WHERE cr.id = ");
	query.append(computer.getId());
	query.append(" AND cy.id = cr.company_id");
	return query.toString();
    }

    private String generateDeleteQuery(Computer computer) {
	StringBuffer query = new StringBuffer();
	query.append("DELETE FROM ");
	query.append("computer");
	query.append(" WHERE id = ");
	query.append(computer.getId());
	return query.toString();
    }
}
