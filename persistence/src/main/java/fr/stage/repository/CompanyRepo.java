package fr.stage.repository;

import org.springframework.data.repository.CrudRepository;

import fr.stage.domain.Company;

public interface CompanyRepo extends CrudRepository<Company, Long>{}
