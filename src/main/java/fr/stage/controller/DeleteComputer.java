package fr.stage.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import fr.stage.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputer {

    @Autowired
    ComputerService computerService;

    // To access Messages_locale.properties
    @Autowired
    ReloadableResourceBundleMessageSource messageSource;

    // To manipulate the cookie to get the locale
    @Autowired
    CookieLocaleResolver cookieLocaleResolver;

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam(value = "id", required = true) long id,
	    HttpServletRequest request) {
	ModelAndView mod = new ModelAndView("redirect:dashboard");
	// redirectAttributes.addFlashAttribute("page", p);
	// Search for computer with that id
	if (computerService.delete(id)) {
	    // If 1 row has been deleted

	    // SET request to UTF-8
	    try {
		request.setCharacterEncoding("UTF-8");
	    }
	    catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }

	    mod.addObject(
		    "successMessage",
		    messageSource.getMessage("successMessage.deleted", null,
			    cookieLocaleResolver.resolveLocale(request)));
	    // return "redirect:dashboard";
	}
	return mod;
    }

}
