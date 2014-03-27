package fr.stage.domainClasses;


public class Company {

    private Long id;

    private String name;

    public Company() {
	super();
	this.id = 0L;
	this.name = "";
    }

    public Company(String name) {
	super();
	this.id = 0L;
	this.name = name;
    }

    public Company(Long id, String name) {
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

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Company other = (Company) obj;
	if (id != other.id)
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
	return "Company [id=" + id + ", name=" + name + "]";
    }

}
