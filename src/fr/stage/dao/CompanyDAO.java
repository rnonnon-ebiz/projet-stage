package fr.stage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import fr.stage.domainClasses.Company;

public class CompanyDAO extends AbstractDAO<Company> {

    private static final CompanyDAO companyDAO = new CompanyDAO();

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
	    e.printStackTrace();
	}
    }

    private String generateInsertQuery(Company company) {
	StringBuffer query = new StringBuffer();
	query.append("INSERT INTO company ");
	query.append(" ( name) VALUES ('");
	query.append(company.getName());
	query.append("')");
	return query.toString();
    }

    private String generateDeleteQuery(Company company) {
	StringBuffer query = new StringBuffer();
	query.append("DELETE FROM ");
	query.append("computer");
	query.append(" WHERE id = ");
	query.append(company.getId());
	return query.toString();
    }

    private String generateFindQuery(Company company) {
	StringBuffer query = new StringBuffer();
	query.append("SELECT * FROM ");
	query.append("company cy");
	query.append(" WHERE cy.id = ");
	query.append(company.getId());
	return query.toString();
    }

}
