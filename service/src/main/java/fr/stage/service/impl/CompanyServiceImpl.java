package fr.stage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.stage.domain.Company;
import fr.stage.exception.DAOException;
import fr.stage.repository.CompanyRepo;
import fr.stage.service.CompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepo companyRepo;

    // Check if company exists
    @Override
    @Transactional(readOnly = true)
    public boolean exist(Long id) throws DAOException {
	return companyRepo.exists(id);
    }

    // Find company by id
    @Override
    @Transactional(readOnly = true)
    public Company find(Long id) throws DAOException {
	return companyRepo.findOne(id);
    }

    // Find all companies in DB
    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll() throws DAOException {
	return (List<Company>) companyRepo.findAll();
    }
}
