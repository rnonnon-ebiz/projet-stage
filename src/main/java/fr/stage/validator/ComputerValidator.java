package fr.stage.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.stage.dto.ComputerDTO;
import fr.stage.service.CompanyService;
import fr.stage.service.ComputerService;

@Service
public class ComputerValidator {

    @Autowired
    CompanyService companyService;

    @Autowired
    ComputerService computerService;

    /*
     * Error Code : 
     *  1 : ComputerDTO is null
     *  2 : Name is null or empty
     *  4 : Introduced incorrectly formatted or doesn't exist
     *  8 : Discontinued incorrectly formatted or doesn't exist
     *  16 : Company doesn't exist
     */
    public byte validForCreate(ComputerDTO computerDTO) {
	byte error = 0;
	if (computerDTO != null) {
	    // Valid Name
	    error += (byte) (validName(computerDTO.getName()) ? 0 : 2);
	    // Valid Introduced Date
	    error += (byte) (validDate(computerDTO.getIntroducedDate()) ? 0 : 4);
	    // Valid Discontinued Date
	    error += (byte) (validDate(computerDTO.getDiscontinuedDate()) ? 0 : 8);
	    // Valid Company
	    error += (byte) (validCompany(computerDTO.getCompany()) ? 0 : 16);
	}
	else {
	    error = 1;
	}
	// Return result
	return error;
    }

    /*
     * Error Code : 
     *  1 : ComputerDTO is null
     *  2 : Name is null or empty
     *  4 : Introduced incorrectly formatted or doesn't exist
     *  8 : Discontinued incorrectly formatted or doesn't exist
     *  16 : Company doesn't exist
     *  32 : Computer doesn't exist (id error)
     */
    public byte validForUpdate(ComputerDTO computerDTO) {
	byte error = 0;
	if (computerDTO != null) {
	    // Valid Name
	    error += (byte) (validName(computerDTO.getName()) ? 0 : 2);
	    // Valid Introduced Date
	    error += (byte) (validDate(computerDTO.getIntroducedDate()) ? 0 : 4);
	    // Valid Discontinued Date
	    error += (byte) (validDate(computerDTO.getDiscontinuedDate()) ? 0 : 8);
	    // Valid Company
	    error += (byte) (validCompany(computerDTO.getCompany()) ? 0 : 16);
	    // Valid Id
	    error += (byte) (existId(computerDTO.getId()) ? 0 : 32);
	}
	else {
	    error = 1;
	}
	// Return result
	return error;
    }

    public boolean existId(String idString) {
	boolean exist = false;
	String regex = "^\\d\\d*$";
	// It should be match the regexp to be valid
	if (idString != null && idString.matches(regex)) {
	    long id = Long.parseLong(idString);
	    // And Should exist in DB
	    exist = computerService.exist(id);
	}
	return exist;
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
	if (date == null || "".equals(date)) {
	    valid = true;
	}
	else {
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    format.setLenient(false);
	    try {
		format.parse(date);
		valid = true;
	    }
	    catch (ParseException e) {
	    }
	}
	return valid;
    }
}
