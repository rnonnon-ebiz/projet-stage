package fr.stage.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import fr.stage.dao.CompanyDAO;
import fr.stage.domain.Company;
import fr.stage.domain.QCompany;
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
	QCompany qCompany = QCompany.company;
	HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
	Company company = query.from(qCompany).where(qCompany.id.eq(id)).uniqueResult(qCompany);

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
	QCompany qCompany = QCompany.company;
	HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
	company = query.from(qCompany).where(qCompany.id.eq(id)).uniqueResult(qCompany);

	logger.debug("End find {}", id);
	return company;
    }

    @Override
    public List<Company> findAll() throws DAOException {
	logger.debug("Start Find All");

	List<Company> companyList = null;
	// Generate query
	QCompany qCompany = QCompany.company;
	HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
	companyList = query.from(qCompany).list(qCompany);

	logger.debug("End Find All");
	return companyList;
    }
}
