package fr.excilys.dao;

import java.sql.Connection;
import java.sql.SQLException;

import fr.excilys.domainClasses.Company;

public class CompanyDAO extends AbstractCRUDManager<Company> {

    private static final CompanyDAO companyDAO = new CompanyDAO();

    private CompanyDAO() {
    }

    public static CompanyDAO getInstance() {
	return companyDAO;
    }

    @Override
    public void create(Company company) throws SQLException {
	// TODO Auto-generated method stub

    }

    public void findBody(Company company, Connection connection) {
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
    public void find(Company company) {
	Connection connection = beforeOperation();
	findBody(company, connection);
	afterOperation();
    }

    private String generateFindQuery(Company company) {
	StringBuffer query = new StringBuffer();
	query.append("SELECT * FROM ");
	query.append("company cy");
	query.append(" WHERE cy.id = ");
	query.append(company.getId());
	return query.toString();
    }

    public Company find(int id) throws SQLException {
	Company comp = new Company();
	comp.setId(id);
	find(comp);
	return comp;
    }

    @Override
    public void update(Company company) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void delete(Company company) throws SQLException {
	// TODO Auto-generated method stub

    }

}
