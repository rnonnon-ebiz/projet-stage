package fr.stage.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.OrderSpecifier;

import fr.stage.dao.ComputerDAO;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.domain.QCompany;
import fr.stage.domain.QComputer;
import fr.stage.exception.DAOException;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
    //Enum used for ordering instead of a big switch
    private enum OrderEnum{
	crNameASC(QComputer.computer.name.asc()),crNameDESC(QComputer.computer.name.desc()),introASC(QComputer.computer.introducedDate.asc()),introDESC(QComputer.computer.introducedDate.desc()),
	discASC(QComputer.computer.discontinuedDate.asc()),discDESC(QComputer.computer.discontinuedDate.desc()),cyNameASC(QCompany.company.name.asc()),cyNameDESC(QCompany.company.name.desc());

	private OrderSpecifier order;

	private OrderEnum(OrderSpecifier order){
	    this.order = order;
	}

	public OrderSpecifier getOrder() {
	    return order;
	}
    }
    @Autowired
    SessionFactory sessionFactory;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public ComputerDAOImpl() {
    }

    @Override
    public boolean exist(long id) throws DAOException {
	logger.debug("Start existence check {}", id);

	boolean computerExistence = false;
	// Generate query
	QComputer computer =  QComputer.computer;
	HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
	Computer compRes = query.from(computer)
		.where(computer.id.eq(id))
		.uniqueResult(computer);

	if(compRes != null){
	    computerExistence = true;
	}

	logger.debug("End existence check {}", id);
	return computerExistence;
    }

    @Override
    public int count(String nameFilter) throws DAOException {
	logger.debug("Start count {}", nameFilter);

	long total = 0L;
	// Generate query
	QComputer computer =  QComputer.computer;
	QCompany company = QCompany.company;
	HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
	query.from(computer).leftJoin(computer.company, company);
	// Condition
	if(nameFilter != null && !nameFilter.isEmpty()) {
	    String restrictionName = "%"+nameFilter+"%";
	    query.where(computer.name.like(restrictionName).or(company.name.like(restrictionName)));
	}
	total = query.count();

	logger.debug("End count {}", nameFilter);
	return (int)total;

    }

    @Override
    public void create(Computer computer) throws DAOException {
	logger.debug("Start create {}", computer);

	sessionFactory.getCurrentSession().persist(computer);

	logger.info("Computer created : " + computer.toString());
	logger.debug("End create {}", computer);
    }

    @Override
    public boolean delete(Long id) throws DAOException {
	logger.debug("Start delete {}", id);

	long nDelete = 0;
	// Generate Query
	QComputer computer =  QComputer.computer;
	HibernateDeleteClause query = new HibernateDeleteClause(sessionFactory.getCurrentSession(), computer).where(computer.id.eq(id));
	nDelete = query.execute();

	logger.debug("End delete {}", id);
	return (nDelete == 1L);
    }

    @Override
    public Computer find(long id) throws DAOException {
	logger.debug("Start find {}", id);

	Computer computer = null;
	// Generate query
	QComputer qComputer =  QComputer.computer;
	HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
	query.from(qComputer).where(qComputer.id.eq(id));

	computer = (Computer) query.uniqueResult(qComputer);

	logger.debug("End find {}", id);
	return computer;
    }

    @Override
    public List<Computer> find(Page page) throws DAOException {
	logger.debug("Start find {}", page);

	List<Computer> computersList;
	// Generate query from page
	QComputer qComputer =  QComputer.computer;
	HibernateQuery query = generateFindQuery(page);


	computersList = (List<Computer>)(query.list(qComputer));

	logger.debug("End find {}", page);
	return computersList;
    }

    @Override
    public void update(Computer computer) throws DAOException {
	logger.debug("Start update {}", computer);

	sessionFactory.getCurrentSession().merge(computer);

	logger.debug("End update {}", computer);
    }

    private HibernateQuery generateFindQuery(Page page) {
	QComputer computer =  QComputer.computer;
	QCompany company = QCompany.company;
	HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
	query.from(computer).leftJoin(computer.company, company);
	// Filter By Name
	String nameFilter = page.getNameFilter();
	if (nameFilter != null && !nameFilter.isEmpty()) {
	    String restrictionName = "%"+nameFilter+"%";
	    query.where(
		    computer.name.like(restrictionName)
		    .or(company.name.like(restrictionName)));
	}

	// LIMIT
	int limit = page.getComputerPerPage();
	if (limit > 0) {
	    query.limit(limit);
	}
	// OFFSET
	int offset = page.computeOffset();
	if (offset >= 0) {
	    query.offset(offset);
	}
	// ORDER BY
	query.orderBy(OrderEnum.values()[page.getOrderBy()].getOrder());

	return query;
    }
}