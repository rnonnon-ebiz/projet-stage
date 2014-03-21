package fr.excilys.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.excilys.domainClasses.Company;
import fr.excilys.domainClasses.Computer;

public class ComputerDao extends AbstractCRUDManager<Computer> {

    private Connection connection = connectionManager.getConnection();

    private static final ComputerDao computerDao = new ComputerDao();

    private ComputerDao() {
    }

    public static ComputerDao getInstance() {
	return computerDao;
    }

    @Override
    public void create(Computer computer) throws SQLException {
	String query = generateInsertQuery(computer);
	Statement stm = connection.createStatement();
	int id;
	ResultSet rs;
	System.out.println(query);
	stm.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
	rs = stm.getGeneratedKeys();
	if (rs.next()) {
	    computer.setId(rs.getInt(1));
	}
    }

    private String generateInsertQuery(Computer computer) {
	StringBuffer query = new StringBuffer();
	query.append("INSERT INTO computer ");
	query.append(" ( name , introduced , discontinued , company_id ) VALUES ('");
	query.append(computer.getName());
	query.append("' , FROM_UNIXTIME(");
	query.append(computer.getIntroducedDate().getTime());
	query.append(") , FROM_UNIXTIME(");
	query.append(computer.getDiscontinuedDate().getTime());
	query.append(") , ");
	query.append(computer.getCompany().getId());
	query.append(" )");
	return query.toString();
    }

    @Override
    public void find(Computer computer) throws SQLException {
	String query = genericFindQuery("computer", computer.getId());// generateFindQuery(computer);
	System.out.println(query);
	ResultSet res;
	Statement stm = connection.createStatement();
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

    private String generateFindQuery(Computer computer) {
	StringBuffer query = new StringBuffer();
	query.append("SELECT o FROM ");
	query.append("computer");
	query.append(" WHERE o.id = ");
	query.append(computer.getId());
	return query.toString();
    }

    @Override
    public void update(Computer computer) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void delete(Computer computer) throws SQLException {
	// TODO Auto-generated method stub

    }

}
