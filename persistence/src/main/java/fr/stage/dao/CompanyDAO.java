package fr.stage.dao;

import java.util.List;

import fr.stage.domain.Company;
import fr.stage.exception.DAOException;

public interface CompanyDAO {

    public boolean exist(long id) throws DAOException;

    public Company find(long id) throws DAOException;

    public List<Company> findAll() throws DAOException;
}
