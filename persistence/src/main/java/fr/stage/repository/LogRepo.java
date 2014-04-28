package fr.stage.repository;

import org.springframework.data.repository.CrudRepository;

import fr.stage.domain.Log;

public interface LogRepo extends CrudRepository<Log, Long>{}
