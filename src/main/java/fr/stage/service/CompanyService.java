package fr.stage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.stage.dao.CompanyDAO;
import fr.stage.domain.Company;
import fr.stage.exception.DAOException;

@Service
@Transactional
public class CompanyService {

    @Autowired
    CompanyDAO companyDAO;

    // Check if company exists
    @Transactional(readOnly = true)
    public boolean exist(long id) throws DAOException {
	return companyDAO.exist(id);
    }

    // Find company by id
    @Transactional(readOnly = true)
    public Company find(long id) throws DAOException {
	return companyDAO.find(id);
    }

    // Find all companies in DB
    @Transactional(readOnly = true)
    public List<Company> findAll() throws DAOException {
	return companyDAO.findAll();
    }
}
