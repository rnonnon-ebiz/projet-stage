package fr.stage.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.stage.dao.impl.CompanyDAOImpl;
import fr.stage.domain.Company;
import fr.stage.exception.DAOException;
import fr.stage.service.CompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDAOImpl companyDAO;

    @Autowired
    SessionFactory sessionFactory;

    // Check if company exists
    @Override
    public boolean exist(long id) throws DAOException {
	return companyDAO.exist(id);
    }

    // Find company by id
    @Override
    public Company find(long id) throws DAOException {
	return companyDAO.find(id);
    }

    // Find all companies in DB
    @Override
    public List<Company> findAll() throws DAOException {
	return companyDAO.findAll();
    }
}
