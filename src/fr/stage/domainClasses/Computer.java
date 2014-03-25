package fr.stage.domainClasses;

import java.util.Date;

public class Computer {

    private int id;

    private String name;

    private Date introducedDate;

    private Date discontinuedDate;

    private Company company;

    public Computer() {
	super();
	id = 0;
	name = "";
	introducedDate = null;
	discontinuedDate = null;
    }

    public Computer(String name, Date introducedDate, Date discontinuedDate,
	    Company company) {
	super();
	this.id = 0;
	this.name = name;
	this.introducedDate = introducedDate;
	this.discontinuedDate = discontinuedDate;
	this.company = company;
    }

    public Computer(int id, String name, Date introducedDate,
	    Date discontinuedDate, Company company) {
	super();
	this.id = id;
	this.name = name;
	this.introducedDate = introducedDate;
	this.discontinuedDate = discontinuedDate;
	this.company = company;
    }

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
    public int hashCode() {
	return id % 20;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Computer other = (Computer) obj;
	if (company == null) {
	    if (other.company != null)
		return false;
	}
	else if (!company.equals(other.company))
	    return false;
	if (discontinuedDate == null) {
	    if (other.discontinuedDate != null)
		return false;
	}
	else if (!discontinuedDate.equals(other.discontinuedDate))
	    return false;
	if (id != other.id)
	    return false;
	if (introducedDate == null) {
	    if (other.introducedDate != null)
		return false;
	}
	else if (!introducedDate.equals(other.introducedDate))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	}
	else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Computer [id=" + id + ", name=" + name + ", introducedDate="
		+ introducedDate + ", discontinuedDate=" + discontinuedDate
		+ ", company=" + company + "]";
    }

}
