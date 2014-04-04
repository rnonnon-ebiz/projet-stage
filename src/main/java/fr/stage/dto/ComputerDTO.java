package fr.stage.dto;

import javax.validation.constraints.NotNull;

public class ComputerDTO {

    private String id;

    @NotNull
    private String name;

    private String introducedDate;

    private String discontinuedDate;

    private String company;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getIntroducedDate() {
	return introducedDate;
    }

    public void setIntroducedDate(String introducedDate) {
	this.introducedDate = introducedDate;
    }

    public String getDiscontinuedDate() {
	return discontinuedDate;
    }

    public void setDiscontinuedDate(String discontinuedDate) {
	this.discontinuedDate = discontinuedDate;
    }

    public String getCompany() {
	return company;
    }

    public void setCompany(String company) {
	this.company = company;
    }

    public String toString() {
	return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate
		+ ", discontinuedDate=" + discontinuedDate + ", company=" + company + "]";
    }
}
