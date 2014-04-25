package fr.stage.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.stage.dao.ComputerDAO;
import fr.stage.domain.Computer;
import fr.stage.domain.Page;
import fr.stage.exception.DAOException;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
    //Enum used for ordering instead of a big switch
    private enum OrderEnum{
	crNameASC(Order.asc("cr.name")),crNameDESC(Order.desc("cr.name")),introASC(Order.asc("cr.introducedDate")),introDESC(Order.desc("cr.introducedDate")),
	discASC(Order.asc("cr.discontinuedDate")),discDESC(Order.desc("cr.discontinuedDate")),cyNameASC(Order.asc("cy.name")),cyNameDESC(Order.desc("cy.name"));

	private Order order;

	private OrderEnum(Order order){
	    this.order = order;
	}

	public Order getOrder() {
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
	Criteria critQuery = sessionFactory.getCurrentSession().createCriteria(Computer.class);
	critQuery.add(Restrictions.eq("id",id));

	Computer computer = (Computer)critQuery.uniqueResult();
	if(computer != null){
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
	Criteria critQuery = sessionFactory.getCurrentSession().createCriteria(Computer.class, "cr");
	critQuery.createAlias("company", "cy", JoinType.LEFT_OUTER_JOIN);
	// Condition
	if(nameFilter != null && !nameFilter.isEmpty()) {
	    String restrictionName = "%"+nameFilter+"%";
	    critQuery.add(Restrictions.or(
		    Restrictions.like("cr.name", restrictionName),
		    Restrictions.like("cy.name", restrictionName))
		    );
	}
	critQuery.setProjection(Projections.rowCount());
	List computers = critQuery.list();
	if(computers != null){
	    total = (long)(computers.get(0));
	}

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

	int nDelete = 0;
	// Generate Query
	String query = "DELETE Computer WHERE id = :id ";

	nDelete = sessionFactory.getCurrentSession().createQuery(query).setLong("id", id).executeUpdate();

	//	Computer comp = new Computer();
	//	comp.setId(id);
	//	sessionFactory.getCurrentSession().delete(comp);

	logger.debug("End delete {}", id);
	return (nDelete == 1);
    }

    @Override
    public Computer find(long id) throws DAOException {
	logger.debug("Start find {}", id);

	Computer computer = null;
	// Generate query
	Criteria critQuery = sessionFactory.getCurrentSession().createCriteria(Computer.class);
	critQuery.add(Restrictions.eq("id",id));

	computer = (Computer) critQuery.uniqueResult();

	logger.debug("End find {}", id);
	return computer;
    }

    @Override
    public List<Computer> find(Page page) throws DAOException {
	logger.debug("Start find {}", page);

	List<Computer> computersList;
	// Generate query from page
	Criteria critQuery = generateFindQuery(page);


	computersList = (List<Computer>)(critQuery.list());

	logger.debug("End find {}", page);
	return computersList;
    }

    @Override
    public void update(Computer computer) throws DAOException {
	logger.debug("Start update {}", computer);

	sessionFactory.getCurrentSession().merge(computer);

	logger.debug("End update {}", computer);
    }

    private Criteria generateFindQuery(Page page) {
	Criteria critQuery = sessionFactory.getCurrentSession().createCriteria(Computer.class, "cr");
	critQuery.createAlias("cr.company", "cy", JoinType.LEFT_OUTER_JOIN);
	// Filter By Name
	String nameFilter = page.getNameFilter();
	if (nameFilter != null && !nameFilter.isEmpty()) {
	    String restrictionName = "%"+nameFilter+"%";
	    critQuery.add(Restrictions.or(
		    Restrictions.like("cr.name", restrictionName),
		    Restrictions.like("cy.name", restrictionName))
		    );
	}

	// LIMIT
	int limit = page.getComputerPerPage();
	if (limit > 0) {
	    critQuery.setMaxResults(limit);
	}
	// OFFSET
	int offset = page.computeOffset();
	if (offset >= 0) {
	    critQuery.setFirstResult(offset);
	}
	// ORDER BY
	critQuery.addOrder(OrderEnum.values()[page.getOrderBy()].getOrder());

	return critQuery;
    }
}