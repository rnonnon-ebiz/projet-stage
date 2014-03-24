package fr.stage.domainClasses;

public class Company {

    private int id;

    private String name;

    public Company() {
	super();
	this.id = 0;
	this.name = "";
    }

    public Company(String name) {
	super();
	this.id = 0;
	this.name = name;
    }

    public Company(int id, String name) {
	super();
	this.id = id;
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return "Company [id=" + id + ", name=" + name + "]";
    }

}
