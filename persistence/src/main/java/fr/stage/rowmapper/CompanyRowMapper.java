package fr.stage.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.stage.domain.Company;

public class CompanyRowMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
	Company company = new Company();
	// Set ID
	company.setId(rs.getLong("id"));
	// Set Name
	company.setName(rs.getString("name"));
	return company;
    }

}
