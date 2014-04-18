package fr.stage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.stage.domain.Computer;
import fr.stage.domain.Page;
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
	    @RequestParam(value = "goTo", required = false, defaultValue = "0") int goTo,
	    @RequestParam(value = "orderBy", required = false, defaultValue = "0") byte orderBy) {
	Page page = new Page();
	// Set limit
	page.setComputerPerPage(LIMIT_PER_PAGE_DEFAULT);
	// Set Search
	page.setNameFilter(search);
	// Set OrderBy
	page.setOrderBy(orderBy);
	// compute TOTAL Res + max Pages
	int total = computerService.count(page.getNameFilter());
	page.setTotalRes(total);
	// Compute max pages
	page.computeMaxPages();
	// Set Go To
	page.setCurrentPage(goTo);
	// Search computers
	List<Computer> computersList = computerService.find(page);
	// Set Computers found
	page.setComputersList(computersList);
	// Set Result
	ModelAndView model = new ModelAndView("dashboard");
	model.addObject("page", page);
	model.addObject("lang", LocaleContextHolder.getLocale());
	model.addObject("successMessage", successMessage);
	model.addObject("search", search);
	return model;
    }
}
