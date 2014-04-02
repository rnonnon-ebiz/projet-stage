package fr.stage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.stage.domain.Company;
import fr.stage.exception.DAOException;

@Repository
public class CompanyDAO {

    public static final String FIND_ALL_QUERY = "SELECT id, name  FROM company";

    @Autowired
    ConnectionManager connectionManager;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public CompanyDAO() {
    }

    public List<Company> findAll() {
	logger.debug("Start Find All");
	Connection connection = connectionManager.getConnection();
	PreparedStatement stm = null;
	ResultSet res = null;

	List<Company> results = new ArrayList<Company>();
	try {
	    // Generate query
	    String query = "SELECT id, name  FROM company";
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);
	    // Execute preparedStatement
	    res = stm.executeQuery();
	    while (res.next()) {
		// Construct Result
		Company company = new Company();
		company.setId(res.getLong("id"));
		company.setName(res.getString("name"));
		results.add(company);
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to findAll", e);
	    throw new DAOException("Failed to find Companies");
	}
	finally {
	    connectionManager.close(res, stm);
	}
	logger.debug("End Find All");
	return results;
    }
}
