package fr.stage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import fr.stage.domain.Computer;
import fr.stage.exception.DAOException;

public interface ComputerRepo extends CrudRepository<Computer, Long>{

    public long countByNameLikeOrCompanyNameLike(String name, String companyName) throws DAOException;

    public Page<Computer> findAllByNameLikeOrCompanyNameLike(String computerName,String companyName,Pageable page) throws DAOException;

}
