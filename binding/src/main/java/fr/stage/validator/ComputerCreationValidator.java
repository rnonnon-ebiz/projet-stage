package fr.stage.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fr.stage.dto.ComputerDTO;
import fr.stage.service.CompanyService;
import fr.stage.service.ComputerService;
import fr.stage.util.DateUtil;

@Service
public class ComputerCreationValidator implements Validator {

    @Autowired
    CompanyService companyService;

    @Autowired
    ComputerService computerService;

    @Override
    public boolean supports(Class<?> clazz) {
	return ComputerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	ComputerDTO computerDTO = (ComputerDTO) target;
	if (computerDTO != null) {
	    // Valid Name
	    if (!validName(computerDTO.getName())) {
		errors.rejectValue("name", "computer.add.name.invalid");
	    }
	    // Valid Introduced Date
	    if (!validDate(computerDTO.getIntroducedDate())) {
		errors.rejectValue("introducedDate", "computer.add.introducedDate.invalid");
	    }
	    // Valid Discontinued Date
	    if (!validDate(computerDTO.getDiscontinuedDate())) {
		errors.rejectValue("discontinuedDate", "computer.add.discontinuedDate.invalid");
	    }
	    // Valid Company
	    if (!validCompany(computerDTO.getCompany())) {
		errors.rejectValue("company", "computer.add.company.inexist");
	    }
	}
	else {
	    errors.reject("computer.add.computer.fatal");
	}
    }

    public static boolean validName(String name) {
	boolean valid = false;
	// Name should be at least 1 char or digit end should start/end with
	// whitespace
	String regex = "(^\\w|^\\w(\\w|\\s)*\\w)$";
	if (name != null && name.matches(regex)) {
	    valid = true;
	}
	return valid;
    }

    public boolean validCompany(String companyId) {
	boolean valid = false;
	String regex = "^\\d\\d*$";
	// Company Id could be null
	if (companyId == null || "null".equals(companyId)) {
	    valid = true;
	}
	// Else it should be match the regexp to be valid
	else if (companyId.matches(regex)) {
	    long id = Long.parseLong(companyId);
	    // And Should exist in DB
	    valid = companyService.exist(id);
	}
	return valid;
    }

    /*
     * Check format and existence
     */
    public static boolean validDate(String date) {
	boolean valid = false;
	if (date == null || date.isEmpty()) {
	    valid = true;
	}
	else {
	    try {
		DateUtil.stringToDate(date, "yyyy-MM-dd");
		valid = true;
	    }
	    catch (IllegalArgumentException e) {
	    }
	}
	return valid;
    }
}
