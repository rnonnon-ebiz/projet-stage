package fr.stage.domain;

import org.joda.time.DateTime;

public class Computer {

    private Long id;

    private String name;

    private DateTime introducedDate;

    private DateTime discontinuedDate;

    private Company company;

    public Computer() {
	super();
	id = 0L;
	name = "";
	introducedDate = null;
	discontinuedDate = null;
    }

    public Computer(String name, DateTime introducedDate, DateTime discontinuedDate, Company company) {
	super();
	this.id = 0L;
	this.name = name;
	this.introducedDate = introducedDate;
	this.discontinuedDate = discontinuedDate;
	this.company = company;
    }

    public Computer(Long id, String name, DateTime introducedDate, DateTime discontinuedDate,
	    Company company) {
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

    public DateTime getIntroducedDate() {
	return introducedDate;
    }

    public void setIntroducedDate(DateTime introducedDate) {
	this.introducedDate = introducedDate;
    }

    public DateTime getDiscontinuedDate() {
	return discontinuedDate;
    }

    public void setDiscontinuedDate(final DateTime discontinuedDate) {
	this.discontinuedDate = discontinuedDate;
    }

    public Company getCompany() {
	return company;
    }

    public void setCompany(final Company company) {
	this.company = company;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	return id.intValue() % 20;
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
	return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate
		+ ", discontinuedDate=" + discontinuedDate + ", company=" + company + "]";
    }

}
