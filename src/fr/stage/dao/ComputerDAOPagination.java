package fr.stage.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import fr.stage.domainClasses.Computer;

public class ComputerDAOPagination implements Serializable {

    private static final ComputerDAO computerDAO = ComputerDAO.getInstance();

    private int offset;

    private int computerPerPage;

    private List<Computer> computersList;

    private int nComputers;

    private int currentPage;

    private int maxPages;

    private String condition;

    public ComputerDAOPagination(int computerPerPage, int offset,
	    String condition) {
	reinit(computerPerPage, offset, condition);
    }

    public ComputerDAOPagination(int computerPerPage) {
	reinit(computerPerPage, 0, "");
    }

    public String getCondition() {
	return condition;
    }

    public void reinit(int computerPerPage, int offset, String condition) {
	this.offset = 0;
	if (computerPerPage < 0)
	    computerPerPage = 1;
	this.computerPerPage = computerPerPage;
	this.condition = condition;
	this.nComputers = computerDAO.count(condition);
	this.computersList = null;
	maxPages = (int) Math.ceil((double) (nComputers)
		/ (double) (computerPerPage));
    }

    public void updatePage() {
	currentPage = (int) ((double) (offset) / (double) (computerPerPage));
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public int getFrontCurrentPage() {
	return currentPage + 1;
    }

    public int getMaxPages() {
	return maxPages;
    }

    public void setMaxPages(int maxPages) {
	this.maxPages = maxPages;
    }

    public int getnComputers() {
	return nComputers;
    }

    public int getOffset() {
	return offset;
    }

    public void setOffset(int offset) {
	this.offset = offset;
    }

    public int getComputerPerPage() {
	return computerPerPage;
    }

    public void setComputerPerPage(int computerPerPage) {
	this.computerPerPage = computerPerPage;
    }

    public List<Computer> getComputersList() {
	return computersList;
    }

    public void setComputersList(List<Computer> computersList) {
	this.computersList = computersList;
    }

    public List<Computer> findAll() {
	StringBuilder conditionPagination = new StringBuilder();
	conditionPagination.append(" ");
	conditionPagination.append(condition);
	conditionPagination.append(" LIMIT ");
	conditionPagination.append(computerPerPage);
	conditionPagination.append(" OFFSET ");
	conditionPagination.append(offset);
	try {
	    computersList = computerDAO.findAll(conditionPagination.toString());
	}
	catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return computersList;
    }

    public List<Computer> next() {
	offset += computerPerPage;
	if (offset > nComputers)
	    offset = nComputers;
	updatePage();
	return findAll();
    }

    public List<Computer> previous() {
	offset -= computerPerPage;
	if (offset < 0)
	    offset = 0;
	updatePage();
	return findAll();
    }

    public List<Computer> goTo(int page) {
	if (page >= 0 && page < maxPages) {
	    this.currentPage = page;
	}
	this.offset = page * this.computerPerPage;
	return findAll();
    }
}
