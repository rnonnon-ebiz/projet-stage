package fr.stage.dao;

import java.sql.SQLException;
import java.util.List;

import fr.stage.domainClasses.Computer;

public class ComputerDAOPagination {

    private static final ComputerDAO computerDAO = ComputerDAO.getInstance();

    private int offset;

    private int computerPerPage;

    private List<Computer> computersList;

    private int nComputers;

    private int actualPage;

    private int maxPage;

    private String condition;

    public ComputerDAOPagination(int computerPerPage, int offset,
	    String condition) {
	this.offset = offset;
	if (computerPerPage < 0)
	    computerPerPage = 1;
	this.computerPerPage = computerPerPage;
	this.condition = condition;
	this.nComputers = computerDAO.count(condition);
	this.computersList = null;
	maxPage = (int) Math.ceil((double) (nComputers)
		/ (double) (computerPerPage));

    }

    public ComputerDAOPagination(int computerPerPage) {
	this.offset = 0;
	if (computerPerPage < 0)
	    computerPerPage = 1;
	this.computerPerPage = computerPerPage;
	this.condition = "";
	this.nComputers = computerDAO.count();
	this.computersList = null;
	maxPage = (int) Math.ceil((double) (nComputers)
		/ (double) (computerPerPage));
    }

    public void updatePage() {
	actualPage = (int) ((double) (offset) / (double) (computerPerPage));
    }

    public int getActualPage() {
	return actualPage;
    }

    public int getMaxPage() {
	return maxPage;
    }

    public void setMaxPage(int maxPage) {
	this.maxPage = maxPage;
    }

    public int getnComputers() {
	return nComputers;
    }

    public void setnComputers(int nComputers) {
	this.nComputers = nComputers;
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
	this.actualPage = page;
	this.offset = page * this.computerPerPage;
	return findAll();
    }
}
