package fr.stage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import fr.stage.domainClasses.Company;
import fr.stage.domainClasses.Computer;
import fr.stage.domainClasses.Page;

public class ComputerDAO extends AbstractDAO<Computer> {

    private static final ComputerDAO computerDao = new ComputerDAO();

    public static final String FIND_ALL_QUERY = "SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName FROM computer cr LEFT JOIN company cy ON cr.company_id = cy.id ";

    // public static final String COUNT_ALL_QUERY =
    // "SELECT COUNT(*) FROM computer cr ";

    private ComputerDAO() {
    }

    public static ComputerDAO getInstance() {
	return computerDao;
    }

    @Override
    protected int countBody(String nameFilter, Connection connection) {
	int total = 0;
	try {
	    String query = generateCountQuery();

	    stm = connection.prepareStatement(query);
	    if (nameFilter == null)
		nameFilter = "";
	    stm.setString(1, "%" + nameFilter + "%");
	    stm.setString(2, "%" + nameFilter + "%");
	    logger.info(stm.toString());
	    res = stm.executeQuery();
	    if (res.next()) {
		total = res.getInt(1);
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to count", e);
	    e.printStackTrace();
	}
	return total;
    }

    @Override
    protected void createBody(Computer computer, Connection connection) {
	try {
	    generateInsertPrepareStatement(computer, connection);
	    stm.executeUpdate();
	    res = stm.getGeneratedKeys();
	    if (res.next()) {
		computer.setId(res.getLong(1));
		logger.info("Computer created : " + computer.toString());
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to create {}", computer, e);
	    e.printStackTrace();
	}
    }

    @Override
    protected void deleteBody(Long id, Connection connection) {
	try {
	    String query = generateDeleteQuery(id);
	    logger.info(query);

	    stm = connection.prepareStatement(query);
	    stm.executeUpdate(query);
	}
	catch (SQLException e) {
	    logger.error("Failed to delete {}", id, e);
	    e.printStackTrace();
	}
    }

    @Override
    protected List<Computer> findBody(Page page, Connection connection) {
	List<Computer> computersList = new ArrayList<Computer>();
	try {
	    generateFindPrepareStatement(page, connection);
	    res = stm.executeQuery();
	    while (res.next()) {
		Computer computer = new Computer();
		computer.setId(res.getLong("computerId"));
		computer.setName(res.getString("computerName"));
		computer.setDiscontinuedDate(res.getDate("discontinued"));
		computer.setIntroducedDate(res.getDate("introduced"));

		Company company = new Company();
		company.setId(res.getLong("company_id"));
		company.setName(res.getString("companyName"));
		computer.setCompany(company);
		computersList.add(computer);
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to find", e);
	    e.printStackTrace();
	}
	return computersList;
    }

    @Override
    protected void updateBody(Computer computer, Connection connection) {
	// TODO Auto-generated method stub

    }

    private void generateInsertPrepareStatement(Computer computer,
	    Connection connection) throws SQLException {
	String query = generateInsertQuery(computer);
	stm = connection.prepareStatement(query,
		Statement.RETURN_GENERATED_KEYS);
	stm.setString(1, computer.getName());

	if (computer.getIntroducedDate() != null) {
	    stm.setLong(2, computer.getIntroducedDate().getTime() / 1000L);
	}
	else
	    stm.setNull(2, Types.NULL);

	if (computer.getDiscontinuedDate() != null) {
	    stm.setLong(3, computer.getDiscontinuedDate().getTime() / 1000L);
	}
	else
	    stm.setNull(3, Types.NULL);

	if (computer.getCompany() != null)
	    stm.setLong(4, computer.getCompany().getId());
	else
	    stm.setNull(4, Types.NULL);

	logger.info(stm.toString());
    }

    private String generateInsertQuery(Computer computer) {
	StringBuilder query = new StringBuilder();
	query.append("INSERT INTO computer ");
	query.append("( name , introduced , discontinued , company_id ) VALUES (? , ? , ? , ?)");
	// query.append(computer.getName());
	// query.append("'");
	// query.append(DateUtils.convertDateToSQLString(",",
	// computer.getIntroducedDate(), "", "NULL"));
	// query.append(DateUtils.convertDateToSQLString(",",
	// computer.getDiscontinuedDate(), "", "NULL"));
	// query.append(" , ");
	// if (computer.getCompany() == null) {
	// query.append("NULL");
	// }
	// else {
	// query.append(computer.getCompany().getId());
	// }
	// query.append(" )");
	return query.toString();
    }

    private String generateCountQuery() {
	StringBuilder query = new StringBuilder();
	query.append("SELECT COUNT(*) FROM ");
	query.append("computer cr LEFT JOIN company cy");
	query.append(" ON cy.id = cr.company_id");
	// Filter By Name
	query.append(" WHERE cr.name like ?");
	query.append(" OR cy.name like ?");
	return query.toString();
    }

    private void generateFindPrepareStatement(Page page, Connection connection)
	    throws SQLException {
	String query = generateFindQuery(page);
	int indexParam = 1;
	stm = connection.prepareStatement(query);
	String nameFilter = page.getNameFilter();
	if (nameFilter == null) {
	    nameFilter = "";
	}
	stm.setString(indexParam++, "%" + nameFilter + "%");
	stm.setString(indexParam++, "%" + nameFilter + "%");
	// LIMIT
	int limit = page.getComputerPerPage();
	if (limit > 0) {
	    stm.setInt(indexParam++, limit);
	}
	// OFFSET
	int offset = page.computeOffset();
	if (offset >= 0) {
	    stm.setInt(indexParam++, offset);
	}
	logger.info(stm.toString());
    }

    private String generateFindQuery(Page page) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName FROM ");
	query.append("computer cr LEFT JOIN company cy");
	query.append(" ON cy.id = cr.company_id");
	// Filter By Name
	query.append(" WHERE cr.name like ?");
	query.append(" OR cy.name like ?");
	// ORDER BY

	// LIMIT
	int limit = page.getComputerPerPage();
	if (limit > 0) {
	    query.append(" LIMIT ?");
	}
	// OFFSET
	int offset = page.computeOffset();
	if (offset >= 0) {
	    query.append(" OFFSET ?");
	}
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

    // public Computer find(int id) throws SQLException {
    // // Set parameters
    // Map<selectParameters, String> parameters = findOneParameters(id);
    // // Execute query
    // List<Computer> list = findList(parameters);
    // Computer res = null;
    // if (!list.isEmpty())
    // res = list.get(0);
    // // Return results
    // return res;
    // }

    // public int count(Map<selectParameters, String> parameters) {
    // // Create connection
    // Connection connection = beforeOperation();
    // // Set params
    // String query = generateSelectQuery(parameters);
    // // Execute query
    // int nComputer = countBody(connection, query);
    // // Clean
    // afterOperation();
    // // Return result
    // return nComputer;
    // }

    // public List<Computer> findList(Map<selectParameters, String> parameters)
    // throws SQLException {
    // // Create connection
    // Connection connection = beforeOperation();
    // // Set params
    // String query = generateSelectQuery(parameters);
    // // Execute query
    // List<Computer> results = findListBody(connection, query);
    // // Clean
    // afterOperation();
    // // Return result
    // return results;
    // }

    // protected int countBody(Connection connection, String query) {
    // int nResults = 0;
    // try {
    // stm = connection.createStatement();
    // res = stm.executeQuery(query);
    // if (res.next()) {
    // nResults = res.getInt(1);
    // }
    // }
    // catch (SQLException e) {
    // logger.error("Failed to count ", e);
    // e.printStackTrace();
    // }
    // return nResults;
    // }

    // protected List<Computer> findListBody(Connection connection, String
    // query) {
    // List<Computer> results = new ArrayList<Computer>();
    // try {
    // System.out.println(query);
    //
    // stm = connection.createStatement();
    // res = stm.executeQuery(query);
    // while (res.next()) {
    // Computer computer = new Computer();
    // int idCompany;
    // computer.setId(res.getInt("computerId"));
    // computer.setDiscontinuedDate(res.getDate("discontinued"));
    // computer.setIntroducedDate(res.getDate("introduced"));
    // computer.setName(res.getString("computerName"));
    // idCompany = res.getInt("company_id");
    // // Generate Company
    // Company company = new Company();
    // company.setId(idCompany);
    // company.setName(res.getString("companyName"));
    // computer.setCompany(company);
    // results.add(computer);
    // }
    // }
    // catch (SQLException e) {
    // logger.error("Failed to findAll", e);
    // e.printStackTrace();
    // }
    // return results;
    // }
    // @Override
    // protected T findBody(Long id, Connection connection) {
    // Computer computer = null;
    // try {
    // String query = generateFindQuery(computer);
    // System.out.println(query);
    //
    // stm = connection.createStatement();
    // res = stm.executeQuery(query);
    // if (res.next()) {
    // computer.setName(res.getString("computerName"));
    // computer.setDiscontinuedDate(res.getDate("discontinued"));
    // computer.setIntroducedDate(res.getDate("introduced"));
    //
    // Company company = new Company();
    // company.setId(res.getInt("company_id"));
    // company.setName(res.getString("companyName"));
    // computer.setCompany(company);
    // }
    // }
    // catch (SQLException e) {
    // logger.error("Failed to find {}", computer, e);
    // e.printStackTrace();
    // }
    // return computer;
    // }

    // protected List<Computer> findAllBody(String query, Connection connection)
    // {
    // List<Computer> results = new ArrayList<Computer>();
    // try {
    // System.out.println(query);
    //
    // stm = connection.createStatement();
    // res = stm.executeQuery(query);
    // while (res.next()) {
    // Computer computer = new Computer();
    // int idCompany;
    // computer.setId(res.getInt("computerId"));
    // computer.setDiscontinuedDate(res.getDate("discontinued"));
    // computer.setIntroducedDate(res.getDate("introduced"));
    // computer.setName(res.getString("computerName"));
    // idCompany = res.getInt("company_id");
    // // Generate Company
    // Company company = new Company();
    // company.setId(idCompany);
    // company.setName(res.getString("companyName"));
    // computer.setCompany(company);
    // results.add(computer);
    // }
    // }
    // catch (SQLException e) {
    // logger.error("Failed to findAll", e);
    // e.printStackTrace();
    // }
    // return results;
    // }
    // public static Map<selectParameters, String> countParameters() {
    // Map<selectParameters, String> parameters = getGenericParametersMap();
    // parameters.put(selectParameters.ATTRIBUTES, "COUNT(*)");
    // parameters.put(selectParameters.TABLE, "computer cr");
    // return parameters;
    // }
    //
    // public static Map<selectParameters, String> findOneParameters(int id) {
    // Map<selectParameters, String> parameters = getGenericParametersMap();
    // parameters
    // .put(selectParameters.ATTRIBUTES,
    // "cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName");
    // parameters.put(selectParameters.TABLE, "computer cr");
    // parameters.put(selectParameters.JOIN,
    // "JOIN company cy ON cr.company_id = cy.id");
    // parameters.put(selectParameters.WHERE, "WHERE cr.id = " + id);
    // return parameters;
    // }
    //
    // public static Map<selectParameters, String> findAllParameters() {
    // Map<selectParameters, String> parameters = getGenericParametersMap();
    // parameters
    // .put(selectParameters.ATTRIBUTES,
    // "cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName");
    // parameters.put(selectParameters.TABLE, "computer cr");
    // parameters.put(selectParameters.JOIN,
    // "JOIN company cy ON cr.company_id = cy.id");
    // return parameters;
    // }

    // protected Computer findBody(int id, Connection connection,
    // Map<selectParameters, String> parameters) {
    // Computer computer = null;
    // try {
    // String query = generateSelectQuery(parameters);
    // stm = connection.createStatement();
    // res = stm.executeQuery(query);
    //
    // if (res.next()) {
    // computer = new Computer();
    // computer.setId(res.getInt("computerId"));
    // computer.setName(res.getString("computerName"));
    // computer.setDiscontinuedDate(res.getDate("discontinued"));
    // computer.setIntroducedDate(res.getDate("introduced"));
    //
    // Company company = new Company();
    // company.setId(res.getInt("company_id"));
    // company.setName(res.getString("companyName"));
    // computer.setCompany(company);
    // }
    // }
    // catch (SQLException e) {
    // logger.error("Failed to find {}", id, e);
    // e.printStackTrace();
    // }
    // return computer;
    // }
}
