package fr.stage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.stage.domain.Computer;
import fr.stage.domain.InputPage;
import fr.stage.service.ComputerService;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {

    private static int LIMIT_PER_PAGE_DEFAULT = 10;

    @Autowired
    ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet(
	    @RequestParam(value = "successMessage", required = false, defaultValue = "") String successMessage,
	    @RequestParam(value = "search", required = false, defaultValue = "") String search,
	    @RequestParam(value = "goTo", required = false, defaultValue = "1") int goTo,
	    @RequestParam(value = "orderBy", required = false, defaultValue = "0") byte orderBy){
	InputPage page = new InputPage();
	// Set limit
	page.setLimit(LIMIT_PER_PAGE_DEFAULT);
	// Set Search
	page.setNameFilter(search);
	// Set OrderBy
	page.setOrderBy(orderBy);
	// Set Go To
	page.setGoTo(goTo);
	// Search computers
	Page<Computer> outputPage = computerService.find(page);
	// Set Result
	ModelAndView model = new ModelAndView("dashboard");
	model.addObject("page", outputPage);
	model.addObject("lang", LocaleContextHolder.getLocale());
	model.addObject("successMessage", successMessage);
	model.addObject("search", search);
	model.addObject("orderBy", orderBy);
	return model;
    }
}
