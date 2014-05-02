package fr.stage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.stage.domain.Company;
import fr.stage.domain.Computer;
import fr.stage.dto.CompanyDTO;
import fr.stage.dto.ComputerDTO;
import fr.stage.mapper.CompanyMapper;
import fr.stage.mapper.ComputerMapper;
import fr.stage.service.CompanyService;
import fr.stage.service.ComputerService;
import fr.stage.validator.ComputerCreationValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CompanyService companyService;

    @Autowired
    ComputerService computerService;

    @Autowired
    ComputerMapper computerMapper;

    @Autowired
    CompanyMapper companyMapper;

    // Edit Validator
    @Autowired
    ComputerCreationValidator computerCreationValidator;

    // To access Messages_locale.properties
    @Autowired
    ResourceBundleMessageSource messageSource;

    // // To manipulate the cookie to get the locale
    // @Autowired
    // CookieLocaleResolver cookieLocaleResolver;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
	binder.setValidator(computerCreationValidator);
    }

    private List<CompanyDTO> getCompanies() {
	List<Company> companies = companyService.findAll();
	List<CompanyDTO> companiesList = new ArrayList<CompanyDTO>();
	for (Company comp : companies) {
	    companiesList.add(companyMapper.toCompanyDTO(comp));
	}
	return companiesList;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(ModelMap model) {
	ComputerDTO computerDTO = new ComputerDTO();
	model.addAttribute("computer", computerDTO);
	model.addAttribute("companiesList", getCompanies());
	return "pages/addComputer";
    }

    // @RequestMapping(method = RequestMethod.POST)
    // protected ModelAndView doPost(ComputerDTO computerDTO) {
    // byte errorCode = computerValidator.validForCreate(computerDTO);
    // // Null Computer Or Company ERROR: Shouldn't produce
    // // Fatal ERROR
    // if ((errorCode & 0x01) == 0x01 || (errorCode & 0x10) == 0x10) {
    // System.out.println("error code : " + Byte.toString(errorCode));
    // throw new RuntimeException();
    // }
    // // If no error
    // else if (errorCode == 0) {
    // // Store in DB
    // Computer computer = computerMapper.toComputer(computerDTO);
    // computerService.create(computer);
    // ModelAndView mod = new ModelAndView("redirect:dashboard");
    // mod.addObject("successMessage", "Computer Successfully added");
    // return mod;
    // }
    // // Error but not fatal
    // else {
    // ModelAndView mod = new ModelAndView("addComputer");
    // mod.addObject("errorCode", errorCode);
    // mod.addObject("computer", computerDTO);
    // mod.addObject("companiesList", getCompanies());
    // return mod;
    // }
    // }
    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@ModelAttribute("computer") @Valid ComputerDTO computerDTO,
	    BindingResult result) {
	if (result.hasGlobalErrors()) {
	    throw new RuntimeException();
	}
	// If no error
	else if (!result.hasErrors()) {
	    // Store in DB
	    Computer computer = computerMapper.toComputer(computerDTO);
	    computerService.create(computer);
	    // Redirect to Dashboard
	    ModelAndView mod = new ModelAndView("redirect:dashboard");

	    // Get locale to obtain the message in desired language
	    Locale loc = LocaleContextHolder.getLocale();
	    mod.addObject("successMessage",
		    messageSource.getMessage("successMessage.added", null, loc));
	    return mod;
	}
	// Error but not fatal
	else {
	    ModelAndView mod = new ModelAndView("pages/addComputer");
	    mod.addObject("computer", computerDTO);
	    mod.addObject("companiesList", getCompanies());
	    return mod;
	}
    }
}
