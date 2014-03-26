package fr.stage.dao;

public class ComputerDAOPaginationFilter extends ComputerDAOPagination {

    private String filterName;

    public ComputerDAOPaginationFilter(String filterName, int computerPerPage,
	    int offset) {
	super(computerPerPage);
	this.filterName = filterName;
	String conditionPagination = "WHERE cr.name REGEXP \"^" + filterName
		+ ".*\" ";
	this.reinit(computerPerPage, 0, conditionPagination);
    }

    public String getFilterName() {
	return filterName;
    }

}
