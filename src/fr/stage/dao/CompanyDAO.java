package fr.stage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.stage.domainClasses.Company;

public class CompanyDAO extends AbstractDAO<Company> {

    private static final CompanyDAO companyDAO = new CompanyDAO();

    public static final String FIND_ALL_QUERY = "SELECT id, name  FROM company";

    private CompanyDAO() {
    }

    public static CompanyDAO getInstance() {
	return companyDAO;
    }

    public Company find(int id) throws SQLException {
	Company comp = new Company();
	comp.setId(id);
	find(comp);
	return comp;
    }

    public List<Company> findAll() throws SQLException {
	return findAll("");
    }

    public List<Company> findAll(String condition) throws SQLException {
	Connection connection = beforeOperation();
	String query = FIND_ALL_QUERY + condition;
	List<Company> results = findAllBody(query, connection);
	afterOperation();
	return results;
    }

    protected List<Company> findAllBody(String query, Connection connection) {
	List<Company> results = new ArrayList<Company>();
	try {
	    System.out.println(query);

	    stm = connection.createStatement();
	    res = stm.executeQuery(query);
	    while (res.next()) {
		// Generate Company
		Company company = new Company();
		company.setId(res.getInt("id"));
		company.setName(res.getString("name"));
		results.add(company);
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to findAll", e);
	    e.printStackTrace();
	}
	return results;
    }

    @Override
    protected void createBody(Company company, Connection connection) {
	try {
	    String query = generateInsertQuery(company);
	    System.out.println(query);

	    stm = connection.createStatement();
	    stm.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	    res = stm.getGeneratedKeys();
	    if (res.next()) {
		company.setId(res.getInt(1));
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to create {}", company, e);
	    e.printStackTrace();
	}
    }

    @Override
    protected void findBody(Company company, Connection connection) {
	try {
	    String query = generateFindQuery(company);
	    System.out.println(query);

	    stm = connection.createStatement();
	    res = stm.executeQuery(query);
	    if (res.next()) {
		company.setName(res.getString("name"));
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to find {}", company, e);
	    e.printStackTrace();
	}
    }

    @Override
    protected void updateBody(Company object, Connection connection) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void deleteBody(Company company, Connection connection) {
	try {
	    String query = generateDeleteQuery(company);
	    System.out.println(query);

	    stm = connection.createStatement();
	    stm.executeUpdate(query);
	}
	catch (SQLException e) {
	    logger.error("Failed to delete {}", company, e);
	    e.printStackTrace();
	}
    }

    private String generateInsertQuery(Company company) {
	StringBuilder query = new StringBuilder();
	query.append("INSERT INTO company ");
	query.append(" ( name) VALUES ('");
	query.append(company.getName());
	query.append("')");
	return query.toString();
    }

    private String generateDeleteQuery(Company company) {
	StringBuilder query = new StringBuilder();
	query.append("DELETE FROM ");
	query.append("computer");
	query.append(" WHERE id = ");
	query.append(company.getId());
	return query.toString();
    }

    private String generateFindQuery(Company company) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT * FROM ");
	query.append("company cy");
	query.append(" WHERE cy.id = ");
	query.append(company.getId());
	return query.toString();
    }

}
