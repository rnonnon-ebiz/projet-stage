package fr.stage.domain;


public class InputPage{

    private int limit;

    private String nameFilter;

    private byte orderBy;

    private int goTo;

    public byte getOrderBy() {
	return orderBy;
    }

    public void setOrderBy(byte orderBy) {
	this.orderBy = orderBy;
    }

    public String getNameFilter() {
	return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
	this.nameFilter = nameFilter;
    }

    public int getLimit() {
	return limit;
    }

    public void setLimit(int limit) {
	this.limit = limit;
    }

    public int getGoTo() {
	return goTo;
    }

    //--GoTo cuz' front initial page start to 0
    public void setGoTo(int goTo) {
	if(goTo>=1) {
	    --goTo;
	}
	else {
	    goTo = 0;
	}
	this.goTo = goTo;
    }

}
