package fr.stage.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import fr.stage.domain.Company;
import fr.stage.domain.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
	Computer computer = new Computer();
	// Set ID
	computer.setId(rs.getLong("computerId"));
	// Set Name
	computer.setName(rs.getString("computerName"));
	// Set Introduced
	Date intro = rs.getDate("introduced");
	if (intro != null) {
	    computer.setIntroducedDate(new DateTime(intro));
	}
	// Set Discontinued
	Date discont = rs.getDate("discontinued");
	if (discont != null) {
	    computer.setDiscontinuedDate(new DateTime(discont));
	}
	// Set Company
	Company company = null;
	Long companyId = rs.getLong("company_id");
	if (companyId != null) {
	    company = new Company();
	    company.setId(companyId);
	    company.setName(rs.getString("companyName"));
	}
	computer.setCompany(company);

	return computer;
    }
}