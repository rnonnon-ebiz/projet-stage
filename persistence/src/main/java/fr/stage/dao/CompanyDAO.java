package fr.stage.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.stage.domain.Company;
import fr.stage.exception.DAOException;
import fr.stage.rowmapper.CompanyRowMapper;

@Repository
public class CompanyDAO {

    public static final String FIND_ALL_QUERY = "SELECT id, name  FROM company";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public CompanyDAO() {
    }

    public boolean exist(long id) throws DAOException {
	logger.debug("Start existence check {}", id);

	boolean companyExistence = false;
	// Generate query
	String query = "SELECT id  FROM company WHERE id = ?";
	// Generate args
	Object[] args = { id };

	try {
	    Long idFound = jdbcTemplate.queryForObject(query, Long.class, args);
	    if (idFound != null) {
		companyExistence = true;
	    }
	}
	catch (DataAccessException e) {
	    logger.error("Failed to check existence", e);
	    throw new DAOException("Failed to check existence");
	}

	logger.debug("End existence check {}", id);
	return companyExistence;
    }

    public Company find(long id) throws DAOException {
	logger.debug("Start find {}", id);

	Company company = null;
	// Generate query
	String query = "SELECT id, name  FROM company WHERE id = ?";
	// Generate args
	Object[] args = { id };
	try {
	    company = jdbcTemplate.queryForObject(query, args, new CompanyRowMapper());
	}
	catch (DataAccessException e) {
	    logger.error("Failed to find", e);
	    throw new DAOException("Failed to find company");
	}

	logger.debug("End find {}", id);
	return company;
    }

    public List<Company> findAll() throws DAOException {
	logger.debug("Start Find All");

	List<Company> companyList = null;
	// Generate query
	String query = "SELECT id, name  FROM company";
	try {
	    companyList = jdbcTemplate.query(query, new CompanyRowMapper());
	}
	catch (DataAccessException e) {
	    logger.error("Failed to findAll", e);
	    throw new DAOException("Failed to find Companies");
	}

	logger.debug("End Find All");
	return companyList;
    }
}
