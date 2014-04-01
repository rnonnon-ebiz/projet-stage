package fr.stage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Page;

public class CompanyDAO extends AbstractDAO<Company> {

    private static final CompanyDAO companyDAO = new CompanyDAO();

    public static final String FIND_ALL_QUERY = "SELECT id, name  FROM company";

    private CompanyDAO() {
    }

    public static CompanyDAO getInstance() {
	return companyDAO;
    }

    public List<Company> findAll() throws SQLException {
	return findAll("");
    }

    public List<Company> findAll(String condition) throws SQLException {
	QueryObjects qObjects = new QueryObjects();
	String query = FIND_ALL_QUERY + condition;
	List<Company> results = findAllBody(query, qObjects);
	qObjects.afterOperation();
	return results;
    }

    protected List<Company> findAllBody(String query, QueryObjects qObjects) {
	List<Company> results = new ArrayList<Company>();
	try {
	    System.out.println(query);
	    qObjects.prepareStatement(query);
	    ResultSet res = qObjects.executeQuery();
	    while (res.next()) {
		// Generate Company
		Company company = new Company();
		company.setId(res.getLong("id"));
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
    protected void createBody(Company company, QueryObjects qObjects) {
	try {
	    String query = generateInsertQuery(company);
	    System.out.println(query);
	    qObjects.prepareStatement(query);
	    ResultSet res = qObjects.executeUpdateGeneratedKeys();
	    if (res.next()) {
		company.setId(res.getLong(1));
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to create {}", company, e);
	    e.printStackTrace();
	}
    }

    @Override
    protected List<Company> findBody(Page page, QueryObjects qObjects) {
	// TODO
	return null;
    }

    @Override
    protected void updateBody(Company object, QueryObjects qObjects) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void deleteBody(Long id, QueryObjects qObjects) {
	try {
	    String query = generateDeleteQuery(id);
	    System.out.println(query);
	    qObjects.prepareStatement(query);
	    qObjects.executeUpdate();
	}
	catch (SQLException e) {
	    logger.error("Failed to delete", e);
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

    private String generateDeleteQuery(Long id) {
	StringBuilder query = new StringBuilder();
	query.append("DELETE FROM ");
	query.append("computer");
	query.append(" WHERE id = ");
	query.append(id);
	return query.toString();
    }

    @SuppressWarnings("unused")
    private String generateFindQuery(Company company) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT * FROM ");
	query.append("company cy");
	query.append(" WHERE cy.id = ");
	query.append(company.getId());
	return query.toString();
    }

    @Override
    protected int countBody(String nameFilter, QueryObjects qObjects) {
	// TODO Auto-generated method stub
	return 0;
    }

}
