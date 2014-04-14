//package fr.stage.validator;
//
//import org.springframework.stereotype.Service;
//import org.springframework.validation.Errors;
//
//import fr.stage.dto.ComputerDTO;
//
//
//public class ComputerUpdateValidator extends ComputerCreationValidator {
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//	return ComputerDTO.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//	ComputerDTO computerDTO = (ComputerDTO) target;
//	if (computerDTO != null) {
//	    // Valid Name
//	    if (!validName(computerDTO.getName())) {
//		errors.rejectValue("name", "invalid");
//	    }
//	    // Valid Introduced Date
//	    if (!validDate(computerDTO.getIntroducedDate())) {
//		errors.rejectValue("introducedDate", "invalid");
//	    }
//	    // Valid Discontinued Date
//	    if (!validDate(computerDTO.getDiscontinuedDate())) {
//		errors.rejectValue("discontinuedDate", "invalid");
//	    }
//	    // Valid Company
//	    if (!validCompany(computerDTO.getCompany())) {
//		errors.rejectValue("company", "invalid");
//	    }
//	}
//	else {
//	    errors.reject("computer.edit.computer.fatal");
//	}
//    }
//
//    public boolean existId(String idString) {
//	boolean exist = false;
//	String regex = "^\\d\\d*$";
//	// It should be match the regexp to be valid
//	if (idString != null && idString.matches(regex)) {
//	    long id = Long.parseLong(idString);
//	    // And Should exist in DB
//	    exist = computerService.exist(id);
//	}
//	return exist;
//    }
//
// }
