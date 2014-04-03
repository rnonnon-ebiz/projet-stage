package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.stage.domain.Company;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.exception.DAOException;

@Repository
public class ComputerDAO {

    @Autowired
    ConnectionManager connectionManager;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public ComputerDAO() {
    }

    public boolean exist(long id) {
	logger.debug("Start existence check {}", id);
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;

	boolean computerExistence = false;
	try {
	    // Generate query
	    String query = "SELECT id  FROM computer WHERE id = ?";
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);
	    stm.setLong(1, id);
	    logger.info(stm.toString());
	    // Execute preparedStatement
	    res = stm.executeQuery();
	    if (res.next()) {
		// Construct Result
		computerExistence = true;
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to check existence", e);
	    throw new DAOException("Failed to check existence");
	}
	finally {
	    connectionManager.close(res, stm);
	}

	logger.debug("End existence check {}", id);
	return computerExistence;
    }

    public int count(String nameFilter) {
	logger.debug("Start count {}", nameFilter);
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;

	int total = 0;
	try {
	    // Generate query
	    String query = "SELECT COUNT(*) FROM computer cr LEFT JOIN company cy ON cy.id = cr.company_id "
		    + "WHERE cr.name like ? OR cy.name like ? ";
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);

	    if (nameFilter == null)
		nameFilter = "";
	    stm.setString(1, "%" + nameFilter + "%");
	    stm.setString(2, "%" + nameFilter + "%");
	    // Execute preparedStatement
	    res = stm.executeQuery();
	    if (res.next()) {
		// Construct Result
		total = res.getInt(1);
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to count", e);
	    throw new DAOException("Failed to count computers");
	}
	finally {
	    connectionManager.close(res, stm);
	}

	logger.debug("End count {}", nameFilter);
	return total;
    }

    public void create(Computer computer) {
	logger.debug("Start create {}", computer);
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;

	try {
	    // Generate preparedStatement
	    stm = generateInsertPrepareStatement(computer, connection);
	    // Execute preparedStatement
	    stm.executeUpdate();
	    res = stm.getGeneratedKeys();
	    if (res.next()) {
		// Construct Result
		computer.setId(res.getLong(1));
		logger.info("Computer created : " + computer.toString());
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to create {}", computer, e);
	    throw new DAOException("Failed to create computer");
	}
	finally {
	    connectionManager.close(res, stm);
	}

	logger.debug("End create {}", computer);
    }

    public boolean delete(Long id) {
	logger.debug("Start delete {}", id);
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	int nDelete = 0;
	try {
	    // Generate query
	    String query = "DELETE FROM computer WHERE id = " + id;
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);
	    // Execute preparedStatement
	    nDelete = stm.executeUpdate();
	}
	catch (SQLException e) {
	    logger.error("Failed to delete {}", id, e);
	    throw new DAOException("Failed to delete computer");
	}
	finally {
	    connectionManager.close(stm);
	}

	logger.debug("End delete {}", id);
	return (nDelete == 1);
    }

    public Computer find(long id) {
	logger.debug("Start find {}", id);
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;

	Computer computer;
	try {
	    computer = new Computer();
	    // Generate query
	    String query = "SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName FROM computer cr LEFT JOIN company cy ON cy.id = cr.company_id WHERE cr.id = ?";
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);
	    stm.setLong(1, id);
	    logger.info(stm.toString());
	    // Execute preparedStatement
	    res = stm.executeQuery();
	    if (res.next()) {
		// Construct Result
		computer.setId(res.getLong("computerId"));
		computer.setName(res.getString("computerName"));
		computer.setDiscontinuedDate(res.getDate("discontinued"));
		computer.setIntroducedDate(res.getDate("introduced"));

		Company company = new Company();
		company.setId(res.getLong("company_id"));
		company.setName(res.getString("companyName"));
		computer.setCompany(company);
	    }
	}
	catch (SQLException e) {
	    computer = null;
	    logger.error("Failed to find", e);
	    throw new DAOException("Failed to find computer");
	}
	finally {
	    connectionManager.close(res, stm);
	}

	logger.debug("End find {}", id);
	return computer;
    }

    public List<Computer> find(Page page) {
	logger.debug("Start find {}", page);
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;

	List<Computer> computersList = new ArrayList<Computer>();
	try {
	    // Generate preparedStatement
	    stm = generateFindPrepareStatement(page, connection);
	    // Execute preparedStatement
	    res = stm.executeQuery();
	    while (res.next()) {
		// Construct Result
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
	    throw new DAOException("Failed to find computers");
	}
	finally {
	    connectionManager.close(res, stm);
	}

	logger.debug("Start find {}", page);
	return computersList;
    }

    public void update(Computer computer) {
	logger.debug("Start update {}", computer);
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;

	try {
	    // Generate query
	    String query = "UPDATE computer SET name = ?,  introduced = FROM_UNIXTIME(?) , discontinued = FROM_UNIXTIME(?), company_id = ? WHERE id = ?";
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);
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
	    stm.setLong(5, computer.getId());
	    // Execute preparedStatement
	    stm.executeUpdate();
	}
	catch (SQLException e) {
	    logger.error("Failed to update {}", computer, e);
	    throw new DAOException("Failed to update computer");
	}
	finally {
	    connectionManager.close(res, stm);
	}

	logger.debug("End update {}", computer);
    }

    private PreparedStatement generateInsertPrepareStatement(Computer computer, Connection connection) throws SQLException {
	// Generate query
	String query = "INSERT INTO computer  ( name , introduced , discontinued , company_id ) "
		+ "VALUES (? , FROM_UNIXTIME(?) , FROM_UNIXTIME(?) , ?)";
	// Generate preparedStatement
	PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

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
	return stm;
    }

    private PreparedStatement generateFindPrepareStatement(Page page, Connection connection) throws SQLException {
	// Generate query
	String query = generateFindQuery(page);
	// Generate preparedStatement
	int indexParam = 1;
	PreparedStatement stm = connection.prepareStatement(query);
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
	return stm;
    }

    private String generateFindQuery(Page page) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName FROM ");
	query.append("computer cr LEFT JOIN company cy");
	query.append(" ON cy.id = cr.company_id");
	// Filter By Name
	query.append(" WHERE cr.name like ?");
	query.append(" OR cy.name like ? ");
	// ORDER BY
	int orderBy = page.getOrderBy();
	switch (orderBy) {
	case 0:// Name
	       // ASC
	    query.append("ORDER BY computerName ASC");
	    break;
	case 1:// Name
	       // DESC
	    query.append("ORDER BY computerName DESC");
	    break;
	case 2:// Introduced
	       // ASC
	    query.append("ORDER BY cr.introduced ASC");
	    break;
	case 3:// Introduced
	       // DESC
	    query.append("ORDER BY cr.introduced DESC");
	    break;
	case 4:// discontinued
	       // ASC
	    query.append("ORDER BY cr.discontinued ASC");
	    break;
	case 5:// discontinued
	       // DESC
	    query.append("ORDER BY cr.discontinued DESC");
	    break;
	case 6:// companyName
	       // ASC
	    query.append("ORDER BY companyName ASC");
	    break;
	case 7:// companyName
	       // DESC
	    query.append("ORDER BY companyName DESC");
	    break;
	}
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
}
