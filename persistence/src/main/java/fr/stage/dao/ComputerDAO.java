package fr.stage.dao;

import java.util.List;

import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.exception.DAOException;

public interface ComputerDAO {

    public boolean exist(long id) throws DAOException;

    public int count(String nameFilter) throws DAOException;

    public void create(Computer computer) throws DAOException;

    public boolean delete(Long id) throws DAOException;

    public Computer find(long id) throws DAOException;

    public List<Computer> find(Page page) throws DAOException;

    public void update(Computer computer) throws DAOException;

}
