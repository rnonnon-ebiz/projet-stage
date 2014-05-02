package fr.stage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.stage.domain.Computer;
import fr.stage.domain.InputPage;
import fr.stage.exception.DAOException;
import fr.stage.repository.ComputerRepo;
import fr.stage.service.ComputerService;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {
    private enum OrderEnum{
	crNameASC(Sort.Direction.ASC,"name"),crNameDESC(Sort.Direction.DESC,"name"),introASC(Sort.Direction.ASC,"introducedDate"),introDESC(Sort.Direction.DESC,"introducedDate"),
	discASC(Sort.Direction.ASC,"discontinuedDate"),discDESC(Sort.Direction.DESC,"discontinuedDate"),cyNameASC(Sort.Direction.ASC,"company.name"),cyNameDESC(Sort.Direction.DESC,"company.name");

	private Sort sort;

	private OrderEnum(Sort.Direction direction,String properties){
	    this.sort = new Sort(direction, properties);
	}

	public Sort getSort() {
	    return sort;
	}
    }
    @Autowired
    ComputerRepo computerRepo;

    @Autowired
    LogServiceImpl logService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true)
    public int count(String nameFilter) throws DAOException {
	logger.debug("Start count");

	// WORK
	int total;
	if(nameFilter == null || nameFilter.isEmpty()) {
	    total = (int) computerRepo.count();
	}
	else {
	    total = (int) computerRepo.countByNameContainingOrCompanyNameContaining(nameFilter,nameFilter);
	}

	logger.debug("End count");
	return total;
    }

    @Override
    @Transactional(readOnly = false)
    public void create(Computer computer) throws DAOException {
	logger.debug("Start Create Transaction");

	// WORK
	computerRepo.save(computer);
	// LOG
	logService.logInfo("INSERT " + computer.toString());

	logger.debug("End Create Transaction");
    }

    // Find By ID
    @Override
    @Transactional(readOnly = true)
    public Computer find(Long id) throws DAOException {
	logger.debug("Start find by id");

	// WORK
	Computer res = computerRepo.findOne(id);

	logger.debug("End find by id");
	return res;
    }

    // Find By Page Parameters
    @Override
    @Transactional(readOnly = true)
    public Page<Computer> find(InputPage inPage) throws DAOException {
	logger.debug("Start find by Page");	

	//Get Sort in byte and translate it to Sort Type thx to OrderEnum
	Sort sort = OrderEnum.values()[inPage.getOrderBy()].getSort();
	//Set request Page
	PageRequest p = new PageRequest(inPage.getGoTo(),inPage.getLimit(), sort);

	// WORK
	String nameFilter = inPage.getNameFilter();
	Page<Computer> outPage = computerRepo.findAllByNameContainingOrCompanyNameContaining(nameFilter,nameFilter, p);
	if(outPage.getTotalPages() > 0 && outPage.getNumber() >= outPage.getTotalPages()){
	    p = new PageRequest(outPage.getTotalPages()-1,inPage.getLimit(), sort);
	    outPage = computerRepo.findAllByNameContainingOrCompanyNameContaining(nameFilter,nameFilter, p);
	}
	logger.debug("End find by Page");
	return outPage;
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Computer computer) throws DAOException {
	logger.debug("Start Update Transaction");

	// WORK
	computerRepo.save(computer);
	// LOG
	logService.logInfo("UPDATE  " + computer.toString());

	logger.debug("End Update Transaction");
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) throws DAOException {
	logger.debug("Start Delete Transaction");

	// WORK
	computerRepo.delete(id);
	// LOG
	logService.logInfo("DELETE " + id);

	logger.debug("End Delete Transaction");
    }
}
