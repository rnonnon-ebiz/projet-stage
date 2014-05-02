package fr.stage.service;

import org.springframework.data.domain.Page;

import fr.stage.domain.Computer;
import fr.stage.domain.InputPage;
import fr.stage.exception.DAOException;

public interface ComputerService {

    public int count(String nameFilter) throws DAOException;

    public void create(Computer computer) throws DAOException;

    public Computer find(Long id) throws DAOException;

    public Page<Computer> find(InputPage page) throws DAOException;

    public void update(Computer computer) throws DAOException;

    public void delete(Long id) throws DAOException;
}
