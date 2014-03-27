package fr.stage.domainClasses;

import java.util.List;

public class Page {

    private int computerPerPage;

    private List<Computer> computersList;

    private int currentPage;

    private int maxPages;

    private String nameFilter;

    private int totalRes;

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

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int goTo) {
	if (maxPages == 0) {
	    goTo = 0;
	}
	else {
	    goTo--;
	    if (goTo >= maxPages)
		goTo = maxPages - 1;
	    if (goTo < 0)
		goTo = 0;
	}
	this.currentPage = goTo;
    }

    public int getMaxPages() {
	return maxPages;
    }

    public void setMaxPages(int maxPages) {
	this.maxPages = maxPages;
    }

    public String getNameFilter() {
	return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
	this.nameFilter = nameFilter;
    }

    public int getTotalRes() {
	return totalRes;
    }

    public void setTotalRes(int totalRes) {
	this.totalRes = totalRes;
    }

    public int getFrontCurrentPage() {
	return currentPage + 1;
    }

    public int computeOffset() {
	return currentPage * this.computerPerPage;
    }

    public void computeMaxPages() {
	if (computerPerPage > 0) {
	    maxPages = (int) Math.ceil((double) (totalRes)
		    / (double) (computerPerPage));
	}
    }
}
