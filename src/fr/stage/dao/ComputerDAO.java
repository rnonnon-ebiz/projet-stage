package fr.stage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Computer;
import fr.stage.utils.DateUtils;

public class ComputerDAO extends AbstractDAO<Computer> {

    private static final ComputerDAO computerDao = new ComputerDAO();

    public static final String FIND_ALL_QUERY = "SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName FROM computer cr LEFT JOIN company cy ON cr.company_id = cy.id ";

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

    public List<Computer> findAll() throws SQLException {
	return findAll("");
    }

    public List<Computer> findAll(String condition) throws SQLException {
	Connection connection = beforeOperation();
	String query = FIND_ALL_QUERY + condition;
	List<Computer> results = findAllBody(query, connection);
	afterOperation();
	return results;
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
		computer.setName(res.getString("computerName"));
		computer.setDiscontinuedDate(res.getDate("discontinued"));
		computer.setIntroducedDate(res.getDate("introduced"));

		Company company = new Company();
		company.setId(res.getInt("company_id"));
		company.setName(res.getString("companyName"));
		computer.setCompany(company);
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to find {}", computer, e);
	    e.printStackTrace();
	}
    }

    protected List<Computer> findAllBody(String query, Connection connection) {
	List<Computer> results = new ArrayList<Computer>();
	try {
	    System.out.println(query);

	    stm = connection.createStatement();
	    res = stm.executeQuery(query);
	    while (res.next()) {
		Computer computer = new Computer();
		int idCompany;
		computer.setId(res.getInt("computerId"));
		computer.setDiscontinuedDate(res.getDate("discontinued"));
		computer.setIntroducedDate(res.getDate("introduced"));
		computer.setName(res.getString("computerName"));
		idCompany = res.getInt("company_id");
		// Generate Company
		Company company = new Company();
		company.setId(idCompany);
		company.setName(res.getString("companyName"));
		computer.setCompany(company);
		results.add(computer);
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to findAll", e);
	    e.printStackTrace();
	}
	return results;
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
	query.append("SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName FROM ");
	query.append("computer cr JOIN company cy");
	query.append(" ON cy.id = cr.company_id WHERE cr.id = ");
	query.append(computer.getId());
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
