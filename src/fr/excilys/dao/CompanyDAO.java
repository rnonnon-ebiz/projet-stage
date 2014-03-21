package fr.excilys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.excilys.domainClasses.Company;

public class CompanyDAO extends AbstractCRUDManager<Company> {

    private Connection connection = connectionManager.getConnection();

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

    @Override
    public void find(Company company) throws SQLException {
	String query = genericFindQuery("company", company.getId());
	System.out.println(query);
	ResultSet res;
	Statement stm = connection.createStatement();
	res = stm.executeQuery(query);
	if (res.next()) {
	    company.setName(res.getString("name"));
	}
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
