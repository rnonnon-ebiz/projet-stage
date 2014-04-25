package fr.stage.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

	Criteria critQuery = sessionFactory.getCurrentSession().createCriteria(Company.class);
	critQuery.add(Restrictions.eq("id",id));

	Company company = (Company)critQuery.uniqueResult();
	if(company != null){
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
	Criteria critQuery = sessionFactory.getCurrentSession().createCriteria(Company.class);
	critQuery.add(Restrictions.eq("id",id));

	company = (Company) critQuery.uniqueResult();

	logger.debug("End find {}", id);
	return company;
    }

    @Override
    public List<Company> findAll() throws DAOException {
	logger.debug("Start Find All");

	List<Company> companyList = null;
	// Generate query
	companyList = sessionFactory.getCurrentSession().createCriteria(Company.class).list();

	logger.debug("End Find All");
	return companyList;
    }
}
