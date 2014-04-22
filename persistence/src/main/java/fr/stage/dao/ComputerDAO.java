package fr.stage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;

import fr.stage.domain.Company;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.exception.DAOException;
import fr.stage.rowmapper.ComputerRowMapper;

@Repository
public class ComputerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Datasource used for simpleJdbcTemplate
    @Autowired
    private BoneCPDataSource dataSource;

    private SimpleJdbcInsert simpleJdbcInsert;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public ComputerDAO() {
    }

    public boolean exist(long id) throws DAOException {
	logger.debug("Start existence check {}", id);

	boolean computerExistence = false;
	// Generate query
	String query = "SELECT id FROM computer WHERE id = ?";
	// Generate args
	Object[] args = { id };

	try {
	    Long idFound = jdbcTemplate.queryForObject(query, Long.class, args);
	    if (idFound != null) {
		computerExistence = true;
	    }
	}
	catch (DataAccessException e) {
	    logger.error("Failed to check existence", e);
	    throw new DAOException("Failed to check existence");
	}

	logger.debug("End existence check {}", id);
	return computerExistence;
    }

    public int count(String nameFilter) throws DAOException {
	logger.debug("Start count {}", nameFilter);

	int total = 0;
	// Generate query
	String query = "SELECT COUNT(*) FROM computer cr LEFT JOIN company cy ON cy.id = cr.company_id "
		+ "WHERE cr.name like ? OR cy.name like ? ";
	// Generate Count Args
	Object[] args = generateCountArgs(nameFilter);

	try {
	    total = jdbcTemplate.queryForObject(query, Integer.class, args);
	}
	catch (DataAccessException e) {
	    logger.error("Failed to count computers");
	    throw new DAOException("Failed to count computers");
	}

	logger.debug("End count {}", nameFilter);
	return total;

    }

    public void create(Computer computer) throws DAOException {
	logger.debug("Start create {}", computer);
	simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("computer")
		.usingGeneratedKeyColumns("id");
	// Generate Insert Args
	Map<String, Object> args = generateInsertArgs(computer);
	// Execute and get generated id
	long id = (Long) simpleJdbcInsert.executeAndReturnKey(args);
	// Set computer id
	computer.setId(id);

	logger.info("Computer created : " + computer.toString());
	logger.debug("End create {}", computer);
    }

    public boolean delete(Long id) throws DAOException {
	logger.debug("Start delete {}", id);

	int nDelete = 0;
	// Generate Query
	String query = "DELETE FROM computer WHERE id = ? ";
	// Generate Args
	Object[] args = { id };

	try {
	    nDelete = jdbcTemplate.update(query, args);
	}
	catch (DataAccessException e) {
	    logger.error("Failed to delete {}", id, e);
	    throw new DAOException("Failed to delete computer");
	}

	logger.debug("End delete {}", id);
	return (nDelete == 1);
    }

    public Computer find(long id) throws DAOException {
	logger.debug("Start find {}", id);

	Computer computer = null;
	// Generate query
	String query = "SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName "
		+ "FROM computer cr LEFT JOIN company cy ON cy.id = cr.company_id "
		+ "WHERE cr.id = ?";
	// Generate Args
	Object[] args = { id };
	try {
	    computer = jdbcTemplate.queryForObject(query, args, new ComputerRowMapper());
	}
	catch (DataAccessException e) {
	    logger.error("Failed to find", e);
	    throw new DAOException("Failed to find computer");
	}

	logger.debug("End find {}", id);
	return computer;
    }

    public List<Computer> find(Page page) throws DAOException {
	logger.debug("Start find {}", page);

	List<Computer> computersList = null;
	// Generate query from page
	String query = generateFindQuery(page);
	// Generate Args from page
	Object[] args = generateFindArgs(page);
	try {
	    computersList = jdbcTemplate.query(query, args, new ComputerRowMapper());
	}
	catch (DataAccessException e) {
	    logger.error("Failed to find", e);
	    throw new DAOException("Failed to find computers");
	}

	logger.debug("End find {}", page);
	return computersList;
    }

    public void update(Computer computer) throws DAOException {
	logger.debug("Start update {}", computer);

	// Generate query
	String query = "UPDATE computer SET name = ?, introduced = ? , discontinued = ?, company_id = ? WHERE id = ?";
	// Generate Args
	Object[] args = generateUpdateArgs(computer);
	try {
	    jdbcTemplate.update(query, args);
	}
	catch (DataAccessException e) {
	    logger.error("Failed to update {}", computer, e);
	    throw new DAOException("Failed to update computer");
	}

	logger.debug("End update {}", computer);
    }

    public Object[] generateCountArgs(String nameFilter) {
	Object[] args = new Object[2];
	String filter;
	if (nameFilter == null)
	    filter = "";
	else
	    filter = "%" + nameFilter + "%";

	args[0] = args[1] = filter;
	return args;
    }

    private Object[] generateUpdateArgs(Computer computer) {
	Object[] args = new Object[5];

	// Name
	args[0] = computer.getName();

	// Introduced date
	if (computer.getIntroducedDate() != null) {
	    args[1] = computer.getIntroducedDate().toDate();
	}
	else
	    args[1] = null;

	// Discontinued date
	if (computer.getDiscontinuedDate() != null) {
	    args[2] = computer.getDiscontinuedDate().toDate();
	}
	else
	    args[2] = null;

	// Company ID
	Company company = computer.getCompany();
	if (company != null)
	    args[3] = company.getId();
	else
	    args[3] = null;

	// Computer ID
	args[4] = computer.getId();

	// Return args
	return args;
    }

    private Map<String, Object> generateInsertArgs(Computer computer) {
	Map<String, Object> args = new HashMap<String, Object>();

	// Name
	args.put("name", computer.getName());

	// Introduced date
	if (computer.getIntroducedDate() != null) {
	    args.put("introduced", computer.getIntroducedDate().toDate());
	}
	else
	    args.put("introduced", null);

	// Discontinued date
	if (computer.getDiscontinuedDate() != null) {
	    args.put("discontinued", computer.getDiscontinuedDate().toDate());
	}
	else
	    args.put("discontinued", null);

	// Company
	Company company = computer.getCompany();
	if (company != null)
	    args.put("company_id", company.getId());
	else
	    args.put("company_id", null);

	// Return args
	return args;
    }

    private Object[] generateFindArgs(Page page) {
	// Generate preparedStatement
	int indexArg = 0;
	// ** Begin Compute max args **
	int maxArgs = 0;
	// If there is a search it use 2 args slots
	String nameFilter = page.getNameFilter();
	if (nameFilter != null && !nameFilter.isEmpty()) {
	    maxArgs = 2;
	}
	int limit = page.getComputerPerPage();
	// If limit > 0 limit is an arg
	if (limit > 0)
	    ++maxArgs;
	int offset = page.computeOffset();
	// If offset >= 0 offset is an arg
	if (offset >= 0)
	    ++maxArgs;
	// **End Compute max args **
	Object[] args = new Object[maxArgs];

	// NAME FILTER
	if (nameFilter != null && !nameFilter.isEmpty()) {
	    // FILTER ON COMPUTER NAME + FILTER ON COMPANY NAME
	    args[indexArg++] = args[indexArg++] = "%" + nameFilter + "%";
	}
	// LIMIT
	if (limit > 0) {
	    args[indexArg++] = limit;
	}
	// OFFSET
	if (offset >= 0) {
	    args[indexArg++] = offset;
	}
	return args;
    }

    private String generateFindQuery(Page page) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT cr.id as computerId, cr.name as computerName, cr.introduced, cr.discontinued, cr.company_id, cy.name as companyName FROM ");
	query.append("computer cr LEFT JOIN company cy");
	query.append(" ON cy.id = cr.company_id ");
	// Filter By Name
	String nameFilter = page.getNameFilter();
	if (nameFilter != null && !nameFilter.isEmpty()) {
	    query.append("WHERE cr.name like ? ");
	    query.append("OR cy.name like ? ");
	}
	// ORDER BY
	// Tuning => Enum
	int orderBy = page.getOrderBy();
	switch (orderBy) {
	case 0:// Name
	       // ASC
	    query.append("ORDER BY computerName ASC ");
	    break;
	case 1:// Name
	       // DESC
	    query.append("ORDER BY computerName DESC ");
	    break;
	case 2:// Introduced
	       // ASC
	    query.append("ORDER BY cr.introduced ASC ");
	    break;
	case 3:// Introduced
	       // DESC
	    query.append("ORDER BY cr.introduced DESC ");
	    break;
	case 4:// discontinued
	       // ASC
	    query.append("ORDER BY cr.discontinued ASC ");
	    break;
	case 5:// discontinued
	       // DESC
	    query.append("ORDER BY cr.discontinued DESC ");
	    break;
	case 6:// companyName
	       // ASC
	    query.append("ORDER BY companyName ASC ");
	    break;
	case 7:// companyName
	       // DESC
	    query.append("ORDER BY companyName DESC ");
	    break;
	}
	// LIMIT
	int limit = page.getComputerPerPage();
	if (limit > 0) {
	    query.append("LIMIT ? ");
	}
	// OFFSET
	int offset = page.computeOffset();
	if (offset >= 0) {
	    query.append("OFFSET ? ");
	}
	return query.toString();
    }
}