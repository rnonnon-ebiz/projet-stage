package fr.stage.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.stage.dao.impl.ComputerDAOImpl;
import fr.stage.dao.impl.LogDAOImpl;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.exception.DAOException;
import fr.stage.service.ComputerService;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {

    @Autowired
    LogDAOImpl logDao;

    @Autowired
    ComputerDAOImpl computerDAO;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = true)
    public int count(String nameFilter) throws DAOException {
	logger.debug("Start count");

	// WORK
	int total = computerDAO.count(nameFilter);

	logger.debug("End count");
	return total;
    }

    @Transactional(readOnly = false)
    public void create(Computer computer) throws DAOException {
	logger.debug("Start Create Transaction");

	// WORK
	computerDAO.create(computer);
	// LOG
	logDao.logInfo("INSERT " + computer.toString());

	logger.debug("End Create Transaction");
    }

    // Find By ID
    @Transactional(readOnly = true)
    public Computer find(long id) throws DAOException {
	logger.debug("Start find by id");

	// WORK
	Computer res = computerDAO.find(id);

	logger.debug("End find by id");
	return res;
    }

    // Find By Page Parameters
    @Transactional(readOnly = true)
    public List<Computer> find(Page page) throws DAOException {
	logger.debug("Start find by Page");

	// WORK
	List<Computer> res = computerDAO.find(page);

	logger.debug("End find by Page");
	return res;
    }

    @Transactional(readOnly = false)
    public void update(Computer computer) throws DAOException {
	logger.debug("Start Update Transaction");

	// WORK
	computerDAO.update(computer);
	// LOG
	logDao.logInfo("UPDATE  " + computer.toString());

	logger.debug("End Update Transaction");
    }

    @Transactional(readOnly = false)
    public boolean delete(Long id) throws DAOException {
	logger.debug("Start Delete Transaction");

	// WORK
	boolean deleteSuccess = computerDAO.delete(id);
	// LOG
	logDao.logInfo("DELETE " + id);

	logger.debug("End Delete Transaction");
	return deleteSuccess;
    }
}
