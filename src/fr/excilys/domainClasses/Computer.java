package fr.excilys.domainClasses;

import java.util.Date;

public class Computer {

    private int id;

    private String name;

    private Date introducedDate;

    private Date discontinuedDate;

    private Company company;

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public Date getIntroducedDate() {
	return introducedDate;
    }

    public void setIntroducedDate(final Date introducedDate) {
	this.introducedDate = introducedDate;
    }

    public Date getDiscontinuedDate() {
	return discontinuedDate;
    }

    public void setDiscontinuedDate(final Date discontinuedDate) {
	this.discontinuedDate = discontinuedDate;
    }

    public Company getCompany() {
	return company;
    }

    public void setCompany(final Company company) {
	this.company = company;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return "Computer [id=" + id + ", name=" + name + ", introducedDate="
		+ introducedDate + ", discontinuedDate=" + discontinuedDate
		+ ", company=" + company + "]";
    }

}
