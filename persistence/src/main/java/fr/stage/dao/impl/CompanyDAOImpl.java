package fr.stage.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.stage.dao.CompanyDAO;
import fr.stage.domain.Company;
import fr.stage.exception.DAOException;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    @Autowired
    SessionFactory sessionFactory;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public CompanyDAOImpl() {
    }

    @Override
    public boolean exist(long id) throws DAOException {
	logger.debug("Start existence check {}", id);

	boolean companyExistence = false;
	// Generate query
	String query = "SELECT id FROM Company WHERE id = :id";

	Iterator iterator = sessionFactory.getCurrentSession().createQuery(query).setLong("id", id).iterate();
	if(iterator.hasNext()){
	    companyExistence = true;
	}

	logger.debug("End existence check {}", id);
	return companyExistence;
    }

    @Override
    public Company find(long id) throws DAOException {
	logger.debug("Start find {}", id);

	Company company = null;
	// Generate query
	String query = "FROM Company WHERE id = :id";

	Iterator<Company> iterator = sessionFactory.getCurrentSession().createQuery(query).setLong("id", id).iterate();
	if(iterator.hasNext()) {
	    company = iterator.next();
	}

	logger.debug("End find {}", id);
	return company;
    }

    @Override
    public List<Company> findAll() throws DAOException {
	logger.debug("Start Find All");

	List<Company> companyList = null;
	// Generate query
	String query = "FROM Company";

	companyList = sessionFactory.getCurrentSession().createQuery(query).list();

	logger.debug("End Find All");
	return companyList;
    }
}
