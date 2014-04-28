package fr.stage.service;

import java.util.List;

import fr.stage.domain.Company;
import fr.stage.exception.DAOException;

public interface CompanyService {

    public boolean exist(Long id) throws DAOException;

    public Company find(Long id) throws DAOException;

    public List<Company> findAll() throws DAOException;

}
