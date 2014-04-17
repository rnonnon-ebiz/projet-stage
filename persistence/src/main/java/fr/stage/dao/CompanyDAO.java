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
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;

import fr.stage.domain.Company;
import fr.stage.exception.DAOException;
import fr.stage.util.ConnectionUtil;

@Repository
public class CompanyDAO {

    public static final String FIND_ALL_QUERY = "SELECT id, name  FROM company";

    @Autowired
    private BoneCPDataSource dataSource;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public CompanyDAO() {
    }

    public boolean exist(long id) throws DAOException {
	logger.debug("Start existence check {}", id);
	Connection connection = DataSourceUtils.getConnection(dataSource);
	PreparedStatement stm = null;
	ResultSet res = null;

	boolean companyExistence = false;
	try {
	    // Generate query
	    String query = "SELECT id  FROM company WHERE id = ?";
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);
	    stm.setLong(1, id);
	    logger.info(stm.toString());
	    // Execute preparedStatement
	    res = stm.executeQuery();
	    if (res.next()) {
		// Construct Result
		companyExistence = true;
	    }
	}
	catch (SQLException e) {
	    logger.error("Failed to check existence", e);
	    throw new DAOException("Failed to check existence");
	}
	finally {
	    ConnectionUtil.close(res, stm);
	}

	logger.debug("End existence check {}", id);
	return companyExistence;
    }

    public Company find(long id) throws DAOException {
	logger.debug("Start find {}", id);
	Connection connection = DataSourceUtils.getConnection(dataSource);
	PreparedStatement stm = null;
	ResultSet res = null;

	Company company;
	try {
	    company = new Company();
	    // Generate query
	    String query = "SELECT id, name  FROM company WHERE id = ?";
	    // Generate preparedStatement
	    stm = connection.prepareStatement(query);
	    stm.setLong(1, id);
	    logger.info(stm.toString());
	    // Execute preparedStatement
	    res = stm.executeQuery();
	    if (res.next()) {
		// Construct Result
		company.setId(res.getLong("id"));
		company.setName(res.getString("name"));
	    }
	}
	catch (SQLException e) {
	    company = null;
	    logger.error("Failed to find", e);
	    throw new DAOException("Failed to find company");
	}
	finally {
	    ConnectionUtil.close(res, stm);
	}

	logger.debug("End find {}", id);
	return company;
    }

    public List<Company> findAll() throws DAOException {
	logger.debug("Start Find All");
	Connection connection = DataSourceUtils.getConnection(dataSource);
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
	    ConnectionUtil.close(res, stm);
	}
	logger.debug("End Find All");
	return results;
    }
}
