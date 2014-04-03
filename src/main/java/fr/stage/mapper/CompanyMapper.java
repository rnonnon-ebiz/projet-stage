package fr.stage.mapper;

import org.springframework.stereotype.Component;

import fr.stage.domain.Company;
import fr.stage.dto.CompanyDTO;

@Component
public class CompanyMapper {

    public Company toCompany(CompanyDTO companyDTO) {
	Company company = new Company();
	if (companyDTO != null) {
	    // Fill Id
	    String id = companyDTO.getId();
	    String regex = "^\\d\\d*$";
	    if (id != null && id.matches(regex)) {
		company.setId(Long.parseLong(id));
	    }
	    // Fill Name
	    company.setName(companyDTO.getName());
	}
	// Return result
	return company;
    }

    public CompanyDTO toCompanyDTO(Company company) {
	CompanyDTO companyDTO = new CompanyDTO();
	if (company != null) {
	    // Fill Id
	    companyDTO.setId(company.getId().toString());
	    // Fill Name
	    companyDTO.setName(company.getName());
	}
	// Return result
	return companyDTO;
    }

}
