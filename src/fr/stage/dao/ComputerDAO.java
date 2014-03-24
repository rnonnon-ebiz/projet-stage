package fr.stage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Computer;
import fr.stage.utils.DateUtils;

public class ComputerDAO extends AbstractDAO<Computer> {

    private static final ComputerDAO computerDao = new ComputerDAO();

    private ComputerDAO() {
    }

    public static ComputerDAO getInstance() {
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
	    logger.error("Failed to create {}", computer, e);
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
	    logger.error("Failed to find {}", computer, e);
	    e.printStackTrace();
	}
    }

    @Override
    protected void updateBody(Computer computer, Connection connection) {
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
	    logger.error("Failed to delete {}", computer, e);
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
	if (computer.getCompany() == null) {
	    query.append("NULL");
	}
	else {
	    query.append(computer.getCompany().getId());
	}
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
