package fr.stage.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.HttpRequestHandler;

import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Page;
import fr.stage.utils.Introspection;

@Repository
public class CompanyDAO extends AbstractDAO<Company> implements
	HttpRequestHandler {

    public static final String FIND_ALL_QUERY = "SELECT id, name  FROM company";

    public CompanyDAO() {
    }

    public List<Company> findAll() throws SQLException {
	return findAll("");
    }

    public List<Company> findAll(String condition) throws SQLException {
	String query = FIND_ALL_QUERY + condition;
	List<Company> results = findAllBody(query);
	return results;
    }

    protected List<Company> findAllBody(String query) {
	List<Company> results = new ArrayList<Company>();
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;
	try {
	    // System.out.println(query);
	    stm = connection.prepareStatement(query);
	    res = stm.executeQuery();
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
	finally {
	    Introspection.closeSafe(res);
	    Introspection.closeSafe(stm);
	}
	return results;
    }

    @Override
    protected void createBody(Company company) {
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;
	try {
	    String query = generateInsertQuery(company);
	    // System.out.println(query);
	    stm = connection.prepareStatement(query,
		    Statement.RETURN_GENERATED_KEYS);
	    stm.executeUpdate();
	    res = stm.getGeneratedKeys();
	    if (res.next()) {
		company.setId(res.getLong(1));
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to create {}", company, e);
	    e.printStackTrace();
	}
	finally {
	    Introspection.closeSafe(res);
	    Introspection.closeSafe(stm);
	}
    }

    @Override
    protected List<Company> findBody(Page page) {
	// TODO
	return null;
    }

    @Override
    protected void updateBody(Company object) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void deleteBody(Long id) {
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	try {
	    String query = generateDeleteQuery(id);
	    stm = connection.prepareStatement(query);
	    // System.out.println(query);
	    stm.executeUpdate();
	}
	catch (SQLException e) {
	    logger.error("Failed to delete", e);
	    e.printStackTrace();
	}
	finally {
	    Introspection.closeSafe(stm);
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
    protected int countBody(String nameFilter) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void handleRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub

    }

}
