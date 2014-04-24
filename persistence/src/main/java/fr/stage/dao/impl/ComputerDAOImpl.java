package fr.stage.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.stage.dao.ComputerDAO;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.exception.DAOException;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
    //Enum used for ordering instead of a big switch
    private enum OrderEnum{
	crNameASC("cr.name ASC "),crNameDESC("cr.name DESC "),introASC("cr.introducedDate ASC "),introDESC("cr.introducedDate DESC "),
	discASC("cr.discontinuedDate ASC "),discDESC("cr.discontinuedDate DESC "),cyNameASC("cy.name ASC "),cyNameDESC("cy.name DESC ");

	private String text;

	private OrderEnum(String text){
	    this.setText(text);
	}

	public String getText() {
	    return text;
	}

	public void setText(String text) {
	    this.text = text;
	}

    }
    @Autowired
    SessionFactory sessionFactory;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public ComputerDAOImpl() {
    }

    @Override
    public boolean exist(long id) throws DAOException {
	logger.debug("Start existence check {}", id);

	boolean computerExistence = false;
	// Generate query
	String query = "SELECT id FROM Computer WHERE id = :id";

	Iterator iterator = sessionFactory.getCurrentSession().createQuery(query).setLong("id", id).iterate();
	if(iterator.hasNext()){
	    computerExistence = true;
	}

	logger.debug("End existence check {}", id);
	return computerExistence;
    }

    @Override
    public int count(String nameFilter) throws DAOException {
	logger.debug("Start count {}", nameFilter);

	// Generate query
	StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM Computer cr LEFT JOIN cr.company cy ");
	Query queryObject;
	// Condition
	if(nameFilter != null && !nameFilter.isEmpty()) {
	    query.append("WHERE cr.name like :nameFilter OR cy.name like :nameFilter ");
	    queryObject = sessionFactory.getCurrentSession().createQuery(query.toString()).setString("nameFilter", "%"+nameFilter+"%");
	}
	else{
	    queryObject = sessionFactory.getCurrentSession().createQuery(query.toString());
	}
	long total = (long) queryObject.list().get(0);

	logger.debug("End count {}", nameFilter);
	return (int)total;

    }

    @Override
    public void create(Computer computer) throws DAOException {
	logger.debug("Start create {}", computer);

	System.out.println(computer);
	sessionFactory.getCurrentSession().persist(computer);

	logger.info("Computer created : " + computer.toString());
	logger.debug("End create {}", computer);
    }

    @Override
    public boolean delete(Long id) throws DAOException {
	logger.debug("Start delete {}", id);

	int nDelete = 0;
	// Generate Query
	String query = "DELETE Computer WHERE id = :id ";
	nDelete = sessionFactory.getCurrentSession().createQuery(query).setLong("id", id).executeUpdate();

	logger.debug("End delete {}", id);
	return (nDelete == 1);
    }

    @Override
    public Computer find(long id) throws DAOException {
	logger.debug("Start find {}", id);

	Computer computer = null;
	// Generate query
	String query = "SELECT cr FROM Computer cr LEFT JOIN cr.company cy WHERE cr.id = :id";

	Iterator<Computer> iterator = sessionFactory.getCurrentSession().createQuery(query).setLong("id", id).iterate();
	if(iterator.hasNext()) {
	    computer = iterator.next();
	}

	logger.debug("End find {}", id);
	return computer;
    }

    @Override
    public List<Computer> find(Page page) throws DAOException {
	logger.debug("Start find {}", page);

	List<Computer> computersList = null;
	// Generate query from page
	String query = generateFindQuery(page);
	Query queryObject;

	// Conditions
	// Name
	String nameFilter = page.getNameFilter();
	if(nameFilter != null && !nameFilter.isEmpty()) {
	    // FILTER ON COMPUTER NAME + FILTER ON COMPANY NAME
	    queryObject = sessionFactory.getCurrentSession().createQuery(query.toString()).setString("nameFilter", "%"+nameFilter+"%");
	}
	else{
	    queryObject = sessionFactory.getCurrentSession().createQuery(query.toString());
	}
	// LIMIT
	int limit = page.getComputerPerPage();
	if (limit > 0) {
	    queryObject.setMaxResults(limit);
	}
	// OFFSET
	int offset = page.computeOffset();
	if (offset >= 0) {
	    queryObject.setFirstResult(offset);
	}
	computersList = queryObject.list();

	logger.debug("End find {}", page);
	return computersList;
    }

    @Override
    public void update(Computer computer) throws DAOException {
	logger.debug("Start update {}", computer);

	sessionFactory.getCurrentSession().merge(computer);

	logger.debug("End update {}", computer);
    }

    private String generateFindQuery(Page page) {
	StringBuilder query = new StringBuilder();
	query.append("SELECT cr FROM Computer cr LEFT JOIN cr.company cy ");
	// Filter By Name
	String nameFilter = page.getNameFilter();
	if (nameFilter != null && !nameFilter.isEmpty()) {
	    query.append("WHERE cr.name like :nameFilter ");
	    query.append("OR cy.name like :nameFilter ");
	}
	// ORDER BY
	query.append("ORDER BY ");
	query.append(OrderEnum.values()[page.getOrderBy()].getText());

	return query.toString();
    }
}