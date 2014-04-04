package fr.stage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.stage.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@Controller("/deleteComputer")
public class DeleteComputer {

    @Autowired
    ComputerService computerService;

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(long id) {
	ModelAndView mod = new ModelAndView("redirect:dashboard");
	// redirectAttributes.addFlashAttribute("page", p);
	// Search for computer with that id
	if (computerService.delete(id)) {
	    // If 1 row has been deleted
	    mod.addObject("successMessage", "Computer Successfully deleted");
	    // return "redirect:dashboard";
	}
	else {
	    // return "redirect:dashboard";
	    mod.addObject("deleteStatus", "error");
	}
	return mod;
    }

}
