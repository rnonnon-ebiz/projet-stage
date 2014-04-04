package fr.stage.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.stage.domain.Company;
import fr.stage.domain.Computer;
import fr.stage.dto.ComputerDTO;
import fr.stage.service.CompanyService;
import fr.stage.util.DateUtil;

@Service
public class ComputerMapper {

    @Autowired
    private CompanyService companyService;

    public Computer toComputer(ComputerDTO computerDTO) {
	Computer computer = new Computer();
	if (computerDTO != null) {
	    // Fill Id
	    String id = computerDTO.getId();
	    String regex = "^\\d\\d*$";
	    if (id != null && id.matches(regex)) {
		computer.setId(Long.parseLong(id));
	    }
	    // Fill Name
	    computer.setName(computerDTO.getName());
	    // Fill Introduced Date
	    String introducedString = computerDTO.getIntroducedDate();
	    if (!"".equals(introducedString)) {
		Date introduced = DateUtil.stringToDate(introducedString);
		computer.setIntroducedDate(introduced);
	    }
	    // Fill Discontinued Date
	    String discontinuedString = computerDTO.getDiscontinuedDate();
	    if (!"".equals(discontinuedString)) {
		Date discontinued = DateUtil.stringToDate(discontinuedString);
		computer.setDiscontinuedDate(discontinued);
	    }
	    // Fill Company
	    String companyIdString = computerDTO.getCompany();
	    // if id is not null
	    if (companyIdString != null) {
		Company c = null;
		// if id is digit format
		if (companyIdString.matches(regex)) {
		    long companyId = Long.parseLong(companyIdString);
		    c = companyService.find(companyId);
		}
		computer.setCompany(c);
	    }
	}
	// Return result
	return computer;
    }

    public ComputerDTO toComputerDTO(Computer computer) {
	ComputerDTO computerDTO = new ComputerDTO();
	if (computer != null) {
	    // Fill Id
	    computerDTO.setId(computer.getId().toString());
	    // Fill Name
	    computerDTO.setName(computer.getName());
	    // Fill Introduced Date
	    Date introduced = computer.getIntroducedDate();
	    if (introduced != null) {
		String introducedString = DateUtil.DateToString(introduced);
		computerDTO.setIntroducedDate(introducedString);
	    }
	    else {
		computerDTO.setIntroducedDate("");
	    }
	    // Fill Discontinued Date
	    Date discontinued = computer.getDiscontinuedDate();
	    if (discontinued != null) {
		String discontinuedString = DateUtil.DateToString(discontinued);
		computerDTO.setDiscontinuedDate(discontinuedString);
	    }
	    else {
		computerDTO.setDiscontinuedDate("");
	    }
	    // Fill Company
	    Company c = computer.getCompany();
	    if (c != null) {
		computerDTO.setCompany(c.getId().toString());
	    }
	    else {
		computerDTO.setCompany("");
	    }
	}
	// Return result
	return computerDTO;
    }
}
